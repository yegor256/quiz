import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is thread safe that is used to read and write contents to and from
 * a file.
 */
public class Parser {

    /**
     * The file that will be parsed
     */
    private final File file;

    /**
     * Constructs a Parser
     *
     * @param file
     *            the file that will be parsed
     */
    public Parser(File file) {
        this.file = file;
    }

    /**
     * Retrieves the contents of the file
     *
     * @return String with the contents of the file
     * @throws IOException
     *             if there is a problem reading the file
     */
    public synchronized String getContent() throws IOException {
        return getContent(false);
    }

    /**
     * Retrieves the contents of the file
     *
     * @param isBasicUnicode
     *            true to indicate the contents should return only standard
     *            unicode values
     * @return the contents of the file as a String
     * @throws IOException
     *             if there is a problem reading the file
     */
    private String getContent(boolean isBasicUnicode) throws IOException {
        if (file == null) {
            return "";
        }

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String output = "";

            int data;
            while ((data = in.read()) > 0) {
                if (!isBasicUnicode || isBasicUnicode && data < 0x80) {
                    output += (char) data;
                }
            }
            return output;
        }
    }

    /**
     * Retrieves the contents of the file without extended unicode characters
     *
     * @return the contents of the file as a String
     * @throws IOException
     *             if there is a problem reading the file
     */
    public synchronized String getContentWithoutUnicode() throws IOException {
        return getContent(true);
    }

    /**
     * Returns the file being parsed
     *
     * @return the File being parsed
     */
    public File getFile() {
        return file;
    }

    /**
     * Saves the supplied content to the file
     *
     * @param content
     *            the content to write to the file
     * @throws IOException
     *             if there is a problem writing to the file
     */
    public synchronized void saveContent(String content) throws IOException {
        if (content != null && file != null) {
            try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
                out.write(content);
            }
        }
    }
}
