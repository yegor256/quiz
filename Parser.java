import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {

    private static final int BUFFER_SIZE = 1024;
    private File file;
    private ReadWriteLock lock;

    /**
     * Constructor created to turn the class immutable
     *
     * @param file
     */
    public Parser(File file) {
        this.file = file;
        this.lock = new ReentrantReadWriteLock();
    }

    /**
     * I changed this method to use read-write lock, buffered reader (faster than byte by byte) and StringBuilder.
     *
     * @return
     * @throws IOException
     */
    public String getContent() throws IOException {
        lock.readLock().lock();

        StringBuilder content = new StringBuilder();
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } finally {
            lock.readLock().unlock();
        }
        return content.toString();
    }

    /**
     * Just changed to use the read-write lock. I didn't get the point of this method.
     *
     * @return
     * @throws IOException
     */
    public String getContentWithoutUnicode() throws IOException {
        lock.readLock().lock();

        StringBuilder output = new StringBuilder();
        try {
            int data;
            FileInputStream i = new FileInputStream(file);
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
        } finally {
            lock.readLock().unlock();
        }
        return output.toString();
    }

    /**
     * Changed to user read-write lock and write the bytes of the string without iteration
     *
     * @param content
     * @throws IOException
     */
    public void saveContent(String content) throws IOException {
        lock.writeLock().lock();
        try {
            new FileOutputStream(file).write(content.getBytes(Charset.defaultCharset()));
        } finally {
            lock.writeLock().unlock();
        }
    }
}