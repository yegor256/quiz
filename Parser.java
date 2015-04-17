import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {
    private transient File file;
    private ReadWriteLock rw = new ReentrantReadWriteLock();

    public void setFile(File f) {
        rw.writeLock().lock();
        file = f;
        rw.writeLock().unlock();
    }

    public File getFile() {
        try {
            rw.readLock().lock();
            return file;
        } finally {
            rw.readLock().unlock();
        }
    }

    public String getContent() throws IOException {
        try {
            rw.readLock().lock();
            try (FileInputStream i = new FileInputStream(file)) {
                String output = "";
                int data;
                while ((data = i.read()) > 0) {
                    output += (char) data;
                }
                return output;
            }

        } finally {
            rw.readLock().unlock();
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        try {
            rw.readLock().lock();
            try (FileInputStream i = new FileInputStream(file)) {
                String output = "";
                int data;
                while ((data = i.read()) > 0) {
                    if (data < 0x80) {
                        output += (char) data;
                    }
                }
                return output;
            }
        } finally {
            rw.readLock().unlock();
        }

    }

    public void saveContent(String content) throws IOException {
        try {
            rw.readLock().lock();
            try (FileOutputStream o = new FileOutputStream(file)) {
                for (int i = 0; i < content.length(); i += 1) {
                    o.write(content.charAt(i));
                }
            }
        } finally {
            rw.readLock().unlock();
        }
    }
}
