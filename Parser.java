import android.provider.MediaStore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.BufferedReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class FileEditor {
    private File file;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public void setFile(File f) {
            writeLock.lock();
            try {
                file = f;
            }finally {
                writeLock.unlock();
            }
    }
    public File getFile() {
            readLock.lock();
        try {
            return file;
        }finally {
            readLock.unlock();
        }
    }
    public String getFileContent(Charset charSet) throws IOException {
        readLock.lock();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = Files.newBufferedReader(file.toPath(),
                    charSet)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        } finally {
            readLock.unlock();
        }
    }
    public void writeToFile(String str, Charset charSet) throws IOException {
        writeLock.lock();
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charSet)) {
            writer.write(str, 0, str.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            writeLock.unlock();
        }
    }
}
