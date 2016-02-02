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

    private ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private File file;
    private static final Parser instance = new Parser();

    private Parser() {
        super();
    }

    public Parser getInstance() {
        return instance;
    }


    public void setFile(File file) {
        rwlock.writeLock().lock();
        try {
            this.file = file;
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    public File getFile() {
        rwlock.readLock().lock();
        try {
            return this.file;
        } finally {
            rwlock.readLock().unlock();
        }
    }

    public String getContent() throws IOException {
        rwlock.readLock().lock();
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            String output = "";
            int data;
            while ((data = in.read()) > 0) {
                output += (char) data;
            }
            return output;
        } finally {
            in.close();
            rwlock.readLock().unlock();
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        rwlock.readLock().lock();
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            String output = "";
            int data;
            while ((data = in.read()) > 0) {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
            in.close();
            return output;
        } finally {
            in.close();
            rwlock.readLock().unlock();
        }
    }

    public void saveContent(String content) throws IOException {
        rwlock.writeLock().lock();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        } finally {
            out.close();
            rwlock.writeLock().lock();
        }
    }
}

