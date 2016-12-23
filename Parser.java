import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {
    private final File file;

    public Parser(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        final StringBuilder output = new StringBuilder();
        lock.readLock().lock();
        try {
            try (final InputStream is = new FileInputStream(file)) {
                final byte[] buf = new byte[BUF_SIZE];
                int readed = 0;
                while ((readed = is.read(buf)) != -1) {
                    output.append(new String(buf, 0, readed));
                }
            }
        } finally {
            lock.readLock().unlock();
        }
        return output.toString();
    }

    public String getContentWithoutUnicode() throws IOException {
        final StringBuilder output = new StringBuilder();
        lock.readLock().lock();
        try {
            try (final InputStream is = new FileInputStream(file)) {
                final byte[] buf = new byte[BUF_SIZE];
                int readed = 0;
                while ((readed = is.read(buf)) != -1) {
                    for (int i = 0; i < readed; i++) {
                        if (buf[i] < MAX_CHAR) {
                            output.append((char) buf[i]);
                        }
                    }
                }
            }
        } finally {
            lock.readLock().unlock();
        }
        return output.toString();
    }

    public void saveContent(String content) throws IOException {
        lock.writeLock().lock();
        try {
            try (final OutputStream os = new FileOutputStream(file)) {
                os.write(content.getBytes());
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private static final byte MAX_CHAR = (byte) 0x80;
    private static final int BUF_SIZE = 4096;
}
