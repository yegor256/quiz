import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  private ReadWriteLock rwLock = new ReentrantReadWriteLock();

  public File getFile() {
    Lock l = rwLock.readLock();
    File file = null;
    l.lock();
    try {
      file = this.file;
    } catch (Exception ex) {
      //...
    } finally {
      l.unlock();
    }
    return file;
  }

  public void setFile(File file) {
    Lock l = rwLock.writeLock();
    l.lock();
    try {
      this.file = file;
    } catch (Exception ex) {
      //...
    } finally {
      l.unlock();
    }
  }

  public String getContent() throws IOException {
    StringBuilder output = new StringBuilder();
    try (FileInputStream i = new FileInputStream(file)) {
      int data;
      while ((data = i.read()) > 0) {
        output.append((char) data);
      }
    }
    return output.toString();
  }

  public String getContentWithoutUnicode() throws IOException {
    StringBuilder output = new StringBuilder();
    try (FileInputStream i = new FileInputStream(file)) {
      int data;
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output.append((char) data);
        }
      }
    }
    return output.toString();
  }

  public void saveContent(String content) throws IOException {
    try (FileOutputStream o = new FileOutputStream(file)) {
      for (int i = 0; i < content.length(); i += 1) {
        o.write(content.charAt(i));
      }
    }
  }
}
}
