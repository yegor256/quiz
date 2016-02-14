import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private File file;

    public void setFile(File f) {
        lock.writeLock().lock();
        try {
            this.file = f;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public File getFile() {
        lock.readLock().lock();
        try {
            return file;
        } finally {
            lock.readLock().unlock();
        }
    }

    public String getContent() throws IOException {
        return readFile(false);
    }

    public String getContentWithoutUnicode() throws IOException {
        return readFile(true);
    }

    public String readFile(boolean filterUtf) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("file is null");
        } else {
            lock.readLock().lock();
            try {
                FileInputStream i = new FileInputStream(file);
                StringBuilder output = new StringBuilder();
                int data;
                while ((data = i.read()) > 0) {
                    if (filterUtf) {
                        if (data < 0x80) {
                            output.append((char) data);
                        }
                    } else {
                        output.append((char) data);
                    }
                }
                return output.toString();
            } finally {
                lock.readLock().unlock();
            }
        }
    }

    public void saveContent(String content) throws IOException {
        lock.writeLock().lock();
        try {
            try (Writer fileWriter = new FileWriter(file, false)) {
                fileWriter.write(content);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}

