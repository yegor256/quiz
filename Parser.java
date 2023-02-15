import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * IO operations with file.
 * This class is thread safe.
 * TODO: Rename the 'Parser' name to 'SyncFileOperations'
 * */
public class Parser {
    private final File file;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    public Parser(File file) {
        this.file = file;
    }

    /**
     * Read file content
     */
    public String getContent() throws IOException {
        readWriteLock.readLock().lock();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = inputStream.read()) > 0) {
                output.append((char) data);
            }
            inputStream.close();
            return output.toString();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * Save content to file
     * */
    public void saveContent(String content) {
        readWriteLock.writeLock().lock();
        try {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(content.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * TODO: Data should be cleaned outside the class.
     **/
    @Deprecated
    public String getContentWithoutUnicode() throws IOException {
        StringBuilder output = new StringBuilder();
        for (char ch : getContent().toCharArray()) {
            if (ch < 0x80) {
                output.append(ch);
            }
        }
        return output.toString();
    }
}
