import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class reads and writes an {@link String} content into his associated file. <br/>
 * This class is thread safe.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
public class Parser {

    private final File file;
    private Object lock;

    public Parser(File file) {
        this.file = file;
        this.lock = new Object();
    }

    /**
     * Returns the associated {@link File} object.
     * 
     * @return {@link File}
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Reads the {@link InputStream} content.
     * 
     * @param inputStream
     * @param withoutUnicode
     * @return {@link String}
     * @throws IOException
     */
    private String getContent(InputStream inputStream, boolean withoutUnicode) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        synchronized (lock) {
            try {
                StringBuffer output = new StringBuffer();
                int data;
                while ((data = inputStream.read()) > 0) {
                    if (!withoutUnicode || data < 0x80) {
                        output.append((char) data);
                    }
                }
                return output.toString();
            } finally {
                inputStream.close();
            }
        }
    }

    /**
     * Returns the file content into {@link String} format.
     * 
     * @return {@link String}
     * @throws IOException
     */
    public String getContent() throws IOException {
        return getContent(new FileInputStream(file), false);
    }

    /**
     * Returns the file content into {@link String} format ignoring unicode characters.
     * 
     * @return {@link String}
     * @throws IOException
     */
    public String getContentWithoutUnicode() throws IOException {
        return getContent(new FileInputStream(file), true);
    }

    /**
     * Overwrites the file content by the {@link String} passed as parameter.
     * 
     * @param content
     * @throws IOException
     */
    public void saveContent(String content) throws IOException {
        saveContent(new FileOutputStream(file), content);
    }

    /**
     * Overwrites the outputStream content by the {@link String} passed as parameter.
     * 
     * @param outputStream
     * @param content
     * @throws IOException
     */
    private void saveContent(OutputStream outputStream, String content) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("OutputStream cannot be null");
        }
        synchronized (lock) {
            try {
                for (int i = 0; i < content.length(); i += 1) {
                    outputStream.write(content.charAt(i));
                }
            } finally {
                outputStream.close();
            }
        }
    }
}
