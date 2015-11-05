import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {

    private final File file;


    public Parser(File file) {
        this.file = file;
    }

    /**
     * Get the file to read to/from
     *
     * @return file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Get content of the file with unicode
     *
     * @return String file content
     * @throws IOException
     */
    public String getContent() throws IOException {
        return getFileContent(false);
    }

    /**
     * Get content of the file without unicode
     *
     * @return String file content
     * @throws IOException
     */
    public String getContentWithoutUnicode() throws IOException {
        return getFileContent(true);
    }

    /**
     * Save content to the file
     *
     * @param content to save
     * @throws IOException
     */
    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            bufferedOutputStream.write(content.getBytes());
        }
    }

    private String getFileContent(boolean withoutUnicode) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            StringBuilder fileContent = new StringBuilder();
            int data;
            while ((data = bufferedInputStream.read()) != -1) {
                if (withoutUnicode && !(data < 0x80)) continue;
                fileContent.append((char) data);
            }
            return fileContent.toString();
        }
    }

}
