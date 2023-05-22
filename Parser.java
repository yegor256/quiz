import reader.PlaneReaderImpl;
import reader.Reader;
import reader.UnicodeReaderImpl;

import java.io.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {
    private final File file;
    private final int UNICODE_END = 0X80;
    ReadWriteLock lock = new ReentrantReadWriteLock();

    public File getFile() {
        return file;
    }

    public Parser(File file) {
        this.file = file;
    }

    public String getContent() {
        return commonContentReader(new PlaneReaderImpl());
    }

    public String getContentWithoutUnicode() {
        return commonContentReader(new UnicodeReaderImpl());
    }

    private String commonContentReader(Reader reader) {
        StringBuilder output = new StringBuilder();
        lock.readLock().lock();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            return reader.getContent(output, i);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
        return "";
    }

    public void saveContent(String content) {
        lock.writeLock().lock();
        try (FileOutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
