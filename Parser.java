import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {
    public static final int UNICODE_THRESHOLD = 0x80;

    private String fileName;
    private ReentrantReadWriteLock fileLocker;

    public Parser(String fileName) {
        super();

        if (fileName == null) {
            throw new IllegalArgumentException("fileName cannot be null");
        }

        this.fileName = fileName;
        this.fileLocker = new ReentrantReadWriteLock();
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getContent() {
        FileInputStream input = null;
        StringBuilder output = new StringBuilder();
        Lock readLock = this.fileLocker.readLock();

        try {
            readLock.lock();
            input = new FileInputStream(this.fileName);
            byte[] data = new byte[1024];
            int read = input.read(data);

            while (read > 0) {
                output.append(new String(data, 0, read));
                read = input.read(data);
            }
        } catch (IOException ioe) {
            System.out.println("File read error");
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {/* do nothing */}
            }
            readLock.unlock();
        }

        return output.toString();
    }

    public String getContentWithoutUnicode() {
        String content = this.getContent();

        if (content == null) {
            return null;
        }

        StringBuilder resultBuilder = new StringBuilder();
        for (char c : content.toCharArray()) {
            if (c < UNICODE_THRESHOLD) {
                resultBuilder.append(c);
            }
        }

        return resultBuilder.toString();
    }

    public boolean saveContent(String content) {
        Lock writeLock = this.fileLocker.writeLock();
        FileWriter fw = null;

        try {
            writeLock.lock();
            fw = new FileWriter(fileName);
            fw.write(content);
            fw.flush();
        } catch (IOException ioe) {
            System.out.println("Error wrting to the file");
            return false;
        } finally {
            try {
                fw.close();
            } catch (IOException e) {/* Do nothing */}
            writeLock.unlock();
        }

        return true;
    }
}
