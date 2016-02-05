import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;


/**
 * This class is thread safe.
 */
public class Parser {

    private final AtomicReference<File> fileAtm = new AtomicReference<>();

    public Parser(final File f) {
        this.fileAtm.set(f);
    }

    public File getFile() {
        return fileAtm.get();
    }

    /**
     * Returns the full content of the file
     *
     * @return
     * @throws IOException
     */
    public String getContent() throws IOException {
        //remove to one single method
        return internalReadFile(true);
    }

    /**
     * Returns the content of the file without unicode
     *
     * @return
     * @throws IOException
     */
    public String getContentWithoutUnicode() throws IOException {
        //remove to one single method
        return internalReadFile(false);
    }

    /**
     * Read the file with unicode or without
     *
     * @param withoutUnicode
     *            - boolean to indicate if is need to read with unicode or not
     * @return
     * @throws IOException
     */
    private String internalReadFile(final boolean withUnicode) throws IOException {
        //stringbuffer instead of using string
        final StringBuffer sb = new StringBuffer();

        //try resources, autommatically close the resource
        try (BufferedReader br = new BufferedReader(new FileReader(getFile()))) {
            int data;
            while ((data = br.read()) > 0) {
                if (withUnicode || data < 0x80) {
                    sb.append((char) data);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Writes all the content in the File
     *
     * @param content
     * @throws IOException
     */
    public void saveContent(final String content) throws IOException {

        try (FileOutputStream o = new FileOutputStream(getFile())) {
            o.write(content.getBytes());
        }
    }

}
