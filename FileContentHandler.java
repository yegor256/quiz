import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileContentHandler implements ContentHandler {

    public static final int NON_UNICODE_MAX_VALUE = 0x7F;
    private static final Logger LOGGER = Logger.getLogger(FileContentHandler.class.getSimpleName());
    public static final int DATA_END_CODE = -1;

    private final File file;
    private final Lock lock = new ReentrantLock();

    public FileContentHandler(File file) {
        this.file = file;
    }

    @Override
    public String getContent(boolean filterUnicode) {
        lock.lock();
        try (InputStream is = new FileInputStream(file)) {
            StringBuilder builder = new StringBuilder(is.available());
            int data;
            while ((data = is.read()) != DATA_END_CODE) {
                if (!shouldFilterData(filterUnicode, data)) {
                    builder.append((char) data);
                }
            }
            return builder.toString();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public void saveContent(String content) {
        if (isEmpty(content)) {
            return;
        }

        lock.lock();
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    private boolean shouldFilterData(boolean filterUnicode, int data) {
        return filterUnicode && data > NON_UNICODE_MAX_VALUE;
    }

    private boolean isEmpty(String content) {
        return content == null || content.isEmpty();
    }

}
