import lombok.Cleanup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void setFile(File f) {
        lock.writeLock().lock();
        file = f;
        lock.writeLock().unlock();
    }

    public File getFile() {
        File returnValue = null;
        lock.readLock().lock();
        returnValue = this.file;
        lock.readLock().unlock();
        return returnValue;
    }

    public String getContent() throws IOException {
        @Cleanup FileInputStream i = new FileInputStream(file);
        StringBuilder output = "";
        int data;
        while ((data = i.read()) > 0) {
            output.append(()char)data);
        }
        return output;
    }

    public String getContent() throws IOException {
        lock.readLock().lock();
        try (
                FileInputStream inputStream = new FileInputStream(file)) {
            StringBuilder outputBuilder = new StringBuilder();
            int data;
            byte[] buffer = new byte[1024];
            while ((data = inputStream.read(buffer)) != -1) {
                outputBuilder.append(new String(buffer, 0, data).toCharArray());
            }
            return outputBuilder.toString();
        } finally {
            lock.readLock().unlock();
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        lock.readLock().lock();
        try (
                FileInputStream inputStream = new FileInputStream(file)) {
            StringBuilder stringBuffer = StringBuilder();
            int data;
            byte[] buffer = new byte[1024];
            Scanner sc = new Scanner(stringBuffer);
            while (sc.hasNextLine()) {
                stringBuffer.append(sc.nextLine());
            }
            return stringBuffer.toString();
        } finally {
            lock.readLock().unlock();
        }
    }

    public String getContentWithoutUnicode() throws IOException {

        lock.readLock().lock();
        try (
                FileInputStream inputStream = new FileInputStream(file)) {
            StringBuffer stringBuffer = "";
            int data;
            while ((data = inputStream.read()) > 0) {
                if (data < 0x80) {
                    stringBuffer.append((char) data);
                }
            }
            return output;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void saveContent(String content) throws IOException {
        lock.writeLock().lock();
        try (FileOutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.getBytes());
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
