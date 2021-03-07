import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * This class is thread safe.
 */
public class Parser {
  private final int BUFFER_SIZE = 4096;
  private final File file;
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  public Parser(File file) {
    this.file = file;
  }
  public File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    return read(false);
  }
  public String getContentWithoutUnicode() throws IOException {
    return read(true);
  }
  public String read(final boolean withoutUnicode) throws IOException {
    readWriteLock.readLock().lock();
    try (BufferedReader reader = new BufferedReader(new FileReader((file)))) {
      StringBuilder sb = new StringBuilder(file.length() < Integer.MAX_VALUE? (int) file.length(): Integer.MAX_VALUE);
      char[] readBuffer = new char[BUFFER_SIZE];
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
      } while (read > 0);
      return sb.toString();
    } finally {
      readWriteLock.readLock().unlock();
    }
  }
  public void saveContent(String content) throws IOException {
    readWriteLock.writeLock().lock();
    try (FileOutputStream o = new FileOutputStream(file)) {
      o.write(content.getBytes(StandardCharsets.UTF_8));
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }
}
