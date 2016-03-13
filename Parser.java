import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is thread safe.
 */
public class Parser {

    public static final int NON_UNICODE_MAX_VALUE = 0x7F;
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getSimpleName());

    private File file;
    private final Lock lock = new ReentrantLock();


    public void setFile(File file) {
        lock.lock();
        this.file = file;
        lock.unlock();
    }

    public File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        lock.lock();
        try (InputStream is = new FileInputStream(file)) {
            StringBuilder builder = new StringBuilder(is.available());
            int data;
            while ((data = is.read()) > 0) {
                builder.append(data);
            }
            return builder.toString();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            throw e;
        } finally {
            lock.unlock();
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        lock.lock();
        try (InputStream is = new FileInputStream(file)) {
            StringBuilder builder = new StringBuilder(is.available());
            int data;
            while ((data = is.read()) > 0) {
                if (data <= NON_UNICODE_MAX_VALUE) {
                    builder.append(data);
                }
            }
            return builder.toString();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            throw e;
        } finally {
            lock.unlock();
        }
    }

    public void saveContent(String content) throws IOException {
        lock.lock();
        try (OutputStream os = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i++) {
                os.write(content.charAt(i));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            throw e;
        } finally {
            lock.unlock();
        }
    }
}
