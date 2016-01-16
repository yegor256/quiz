import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class ThreadSafeFileRedactor {

  private File file;

  private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  public ThreadSafeFileRedactor(File file) {
    this.file = file;
  }

  public String getContent() throws IOException {
    try {
      lock.readLock().lock();

      StringBuilder output = new StringBuilder();
      List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
      for (String line : lines) {
        output.append(line).append("\n");
      }
      return output.toString();
    } finally {
      lock.readLock().unlock();
    }
  }

  public String getContentWithoutUnicode() throws IOException {
    try (FileInputStream i = new FileInputStream(file);) {
      lock.readLock().lock();

      StringBuilder output = new StringBuilder();
      int data;
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output.append((char) data);
        }
      }
      return output.toString();
    } finally {
      lock.readLock().unlock();
    }
  }

  public void saveContent(String content) throws IOException {
    try {
      lock.writeLock().lock();
      Files.write(file.toPath(), content.getBytes());
    } finally {
      lock.writeLock().unlock();
    }
  }

  public File getFile() {
    return file;
  }
}
