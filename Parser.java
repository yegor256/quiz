import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    public synchronized void setFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    /**
     * Fetch content
     *
     * @return String
     * @throws IOException
     */
    public String getContent() throws IOException {
        return getContent(true);
    }

    /**
     * Fetch content without unicode
     *
     * @return String
     * @throws IOException
     */
    public String getContentWithoutUnicode() throws IOException {
        return getContent(false);
    }

    private String getContent(boolean withUnicode) throws IOException {
        String output = "";
        try (FileInputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (withUnicode || data < 0x80) {
                    output += data;
                }
            }
        }
        return output;
    }

    /**
     * Save content to the file
     *
     * @param content String
     * @throws IOException
     */
    public void saveContent(String content) throws IOException {
        try (FileOutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
    }
}
