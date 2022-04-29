import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeFileAccessor implements FileReader, FileWriter {
    private final FileReader reader;
    private final FileWriter writer;
    private final ReadWriteLock lock;

    public ThreadSafeFileAccessor(File file) {
        this(file, StandardCharsets.UTF_8);
    }

    public ThreadSafeFileAccessor(File file, Charset charset) {
        this(new SimpleFileReader(file, charset), new SimpleFileWriter(file, charset));
    }

    public ThreadSafeFileAccessor(FileReader reader, FileWriter writer) {
        this(reader, writer, new ReentrantReadWriteLock());
    }

    public ThreadSafeFileAccessor(FileReader reader, FileWriter writer, ReadWriteLock lock) {
        this.reader = Objects.requireNonNull(reader, "reader must be non-null");
        this.writer = Objects.requireNonNull(writer, "writer must be non-null");
        this.lock = Objects.requireNonNull(lock, "lock must be non-null");
        if (!Objects.equals(reader.file(), writer.file())) {
            throw new IllegalArgumentException("reader and writer must access the same file");
        }
    }

    @Override
    public File file() {
        return reader.file();
    }

    @Override
    public String readContent() throws IOException {
        lock.readLock().lock();
        try {
            return reader.readContent();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void saveContent(String content) throws IOException {
        lock.writeLock().lock();
        try {
            writer.saveContent(content);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
