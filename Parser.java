import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {
    private final int READ_BUFFER_SIZE = 4096;
    private final int WRITE_BUFFER_SIZE = 4096;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private volatile File file;

    public void setFile(File file) {
        readWriteLock.writeLock().lock();
        this.file = file;
        readWriteLock.writeLock().unlock();
    }

    public File getFile() {
        return file;
    }

    public String getContent() throws IOException, InterruptedException {
        return read(false);
    }

    public String getContentWithoutUnicode() throws IOException, InterruptedException {
        return read(true);
    }

    public String read(final boolean withoutUnicode) throws IOException, InterruptedException {
        readWriteLock.readLock().lock();
        try (BufferedReader reader = new BufferedReader(new FileReader((file)))) {
            long fileLength = file.length();
            if (fileLength > Integer.MAX_VALUE) {
                throw new IOException("File is too big for String, " + fileLength + " bytes.");
            } else if (fileLength == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder((int) fileLength);
            char[] readBuffer = new char[READ_BUFFER_SIZE];
            int sbIndex = 0;
            int read;
            do {
                read = reader.read(readBuffer);
                if (read > 0) {
                    if (withoutUnicode) {
                        for (int i = 0; i < read; i++) {
                            char c = readBuffer[i];
                            if (c < 0x80) {
                                sb.insert(sbIndex++, c);
                            }
                        }
                    } else {
                        sb.append(readBuffer, 0, read);
                    }
                }
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("File was not read");
                }
            } while (read > 0);
            return sb.toString();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void saveContent(String content) throws IOException, InterruptedException {
        readWriteLock.writeLock().lock();
        try (FileOutputStream o = new FileOutputStream(file)) {
            byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < (contentBytes.length + 1) / WRITE_BUFFER_SIZE; i++) {
                int offset = Math.min(contentBytes.length - i * WRITE_BUFFER_SIZE, WRITE_BUFFER_SIZE);
                o.write(contentBytes, i * WRITE_BUFFER_SIZE, i * WRITE_BUFFER_SIZE + offset);
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("File was not written");
                }
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
