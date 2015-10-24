import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is more thread safe.
 */
public class Parser {
    private File file;

    /**
     * Constructor.
     * @param f file
     */
    public Parser(final File f) {
        this.file = f;
    }

    /**
     * Returns file content
     * @return file content
     * @throws IOException if any I/O error occurs
     */
    public String getContent() throws IOException {
        final StringBuilder output = new StringBuilder((int) this.file.length());
        this.read(new Parser.Function() {
            @Override
            public void process(final int data) {
                output.append((char) data);
            }
        });
        return output.toString();
    }

    /**
     * Returns file non-unicode content
     * @return file content without unicode characters
     * @throws IOException if any I/O error occurs
     */
    public String getContentWithoutUnicode() throws IOException {
        final StringBuilder output = new StringBuilder((int) this.file.length());
        this.read(new Parser.Function() {
            @Override
            public void process(final int data) {
                if (data < 0x80) {
                    output.append((char)data);
                }
            }
        });
        return output.toString();
    }

    /**
     * Saves file content.
     * @param content content
     * @throws IOException if any I/O error occurs
     */
    public void saveContent(final String content) throws IOException {
        try(final FileOutputStream o = new FileOutputStream(this.file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
    }

    /**
     * Reads and processes the content being read.
     * @param function processing funnction
     * @throws IOException if any I/O error occurs
     */
    private void read(final Parser.Function function) throws IOException {
        try (final FileInputStream i = new FileInputStream(this.file)) {
            final StringBuilder output = new StringBuilder(i.available());
            int data;
            while ((data = i.read()) > 0) {
                function.process(data);
            }
        }
    }


    /**
     * Read data prcoessor.
     */
    private interface Function {
        /**
         * Invokes each time data read.
         * @param data data being read
         */
        void process(int data);
    }
}
