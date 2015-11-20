import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;

/**
 * This class is thread safe.
 */
public final class Parser {
    private static final int EOF_MARKER = -1;
    private static final int FIRST_NON_ASCII_CODE = 0x80;

    private final File file;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = this.readWriteLock.readLock();
    private final Lock writeLock = this.readWriteLock.writeLock();

    public Parser(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public String getContent() throws IOException {
        return this.readContent(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return this.readContent(data -> data < FIRST_NON_ASCII_CODE);
    }

    public void saveContent(final String content) throws IOException {
        this.writeLock.lock();
        try (FileOutputStream outputStream = new FileOutputStream(this.file)) {
            outputStream.write(content.getBytes(StandardCharsets.UTF_8));
        } finally {
            this.writeLock.unlock();
        }
    }

    private String readContent(final Predicate<Integer> predicate)
        throws IOException {
        final StringBuilder output = new StringBuilder();
        this.readLock.lock();
        try (FileInputStream inputStream = new FileInputStream(this.file)) {
            int data;
            while ((data = inputStream.read()) > EOF_MARKER) {
                if (predicate.test(data)) {
                    output.append((char) data);
                }
            }
        } finally {
            this.readLock.unlock();
        }
        return output.toString();
    }
}
