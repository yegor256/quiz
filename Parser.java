import java.io.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    private ReentrantReadWriteLock lock;

    public Parser(File file) {
        updateFile(file);
    }

    public synchronized void setFile(File f) {
        updateFile(f);
    }

    private void updateFile(File file) {
        this.file = file;
        this.lock = new ReentrantReadWriteLock();
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        lock.readLock().lock();
        try {
            //Assume the file to parse is a text file with new lines
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();

            for (String line; (line = reader.readLine()) != null; ) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            lock.readLock().unlock();
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        lock.readLock().lock();
        try {
            //Assume the file to parse is a text file with new lines
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();

            for (String line; (line = reader.readLine()) != null; ) {
                sb.append(line.replaceAll("[^\\x00-\\x7F]", "")); //Filter out everything 0x80 and over
            }
            return sb.toString();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void saveContent(String content) throws IOException {
        lock.writeLock().lock();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            writer.write(content);
        } finally {
            writer.flush();
            writer.close();
            lock.writeLock().unlock();
        }
    }
}
