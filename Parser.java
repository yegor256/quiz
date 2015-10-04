import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * This class is thread safe.
 */
public final class Parser {

    private final static Logger LOGGER = Logger.getLogger(Parser.class.getName());
    private final File file;

    /**
     * Set the file to parse when creating the Parser object
     * @param file
     */
    public Parser(final File file) {
        this.file = file;
    }

    /**
     *
     * @return file set in Parser
     */
    public synchronized File getFile() {
        return this.file;
    }

    /**
     *
     * @return the content of the file set in Parser
     * @throws IOException
     */
    public synchronized String getContent() throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(this.file);
            final StringBuilder output = new StringBuilder();
            int data;
            while ((data = fileInputStream.read()) > 0) {
                output.append((char) data);
            }
            return output.toString();
        } catch (IOException exception) {
            throw exception;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }
    }

    /**
     *
     * @return the content of the file set in Parser without unicode
     * @throws IOException
     */
    public synchronized String getContentWithoutUnicode() throws IOException {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(this.file);
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = fileInputStream.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
            return output.toString();
        } catch (IOException exception) {
            LOGGER.warning(exception.getMessage());
            throw exception;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException ex) {
                LOGGER.warning(ex.getMessage());
                throw ex;
            }
        }
    }

    /**
     * Save content to the file set in Parser
     * @param content
     * @throws IOException
     */
    public synchronized void saveContent(final String content) throws IOException {

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(this.file);
            for (int i = 0; i < content.length(); i++) {
                fileOutputStream.write(content.charAt(i));
                fileOutputStream.flush();
            }
        } catch (IOException exception) {
            LOGGER.warning(exception.getMessage());
            throw exception;
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException ex) {
                LOGGER.warning(ex.getMessage());
                throw ex;
            }
        }
    }
}
