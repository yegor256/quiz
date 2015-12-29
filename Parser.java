import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This is the parser class. This class is thread safe.
 */
public class Parser {

    private static final int HEX_128 = 0x80;
    private File file;

    /**
     * Setter of file field
     * 
     * @param file
     *            is the file object
     */
    public synchronized void setFile(final File file) {
        this.file = file;
    }

    /**
     * Getter of file field
     * 
     * @return the file field
     */
    public synchronized File getFile() {
        return this.file;
    }

    /**
     * Reads the file field content, and returns string content of it.
     * 
     * @return String content of the file
     * @throws IOException
     *             in case of any I/O error occurs. Should be handled at the
     *             place of usage
     */
    public String getContent() throws IOException {
        synchronized (file) {
            try (final FileInputStream inputStream = new FileInputStream(file)) {
                final StringBuilder output = new StringBuilder();
                int data;

                while ((data = inputStream.read()) > 0) {
                    output.append(data);
                }

                return output.toString();
            }
        }
    }

    /**
     * Reads the file field content, and returns string content of it. Without
     * unicode characters.
     * 
     * @return String content of the file
     * @throws IOException
     *             in case of any I/O error occurs. Should be handled at the
     *             place of usage.
     */
    public String getContentWithoutUnicode() throws IOException {
        synchronized (file) {
            try (final FileInputStream inputStream = new FileInputStream(file)) {
                final StringBuilder output = new StringBuilder();
                int data;

                while ((data = inputStream.read()) > 0) {
                    if (data < HEX_128) {
                        output.append(data);
                    }
                }
                return output.toString();
            }
        }
    }

    /**
     * Saves the given String content to a file.
     * @param content is the String content to save
     * @throws IOException in case of any I/O problem occurs. Should be handled at the
     *             place of usage.
     */
    public void saveContent(String content) throws IOException {
        synchronized (file) {
            try (final FileOutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(content.getBytes());
            }
        }
    }
}
