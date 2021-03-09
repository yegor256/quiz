import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * This class is thread safe.
 */
public class Parser {
  private final int BUFFER_SIZE = 4096;
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  private File file;
  public void setFile(File file) {
    readWriteLock.writeLock().lock();
    this.file = file;
    readWriteLock.writeLock().unlock();
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
      long fileLength = file.length();
      if (fileLength > Integer.MAX_VALUE) {
        throw new IOException("File is too big for String, " + fileLength + " bytes.");
      } else if (fileLength == 0) {
        return "";
      }
      StringBuilder sb = new StringBuilder((int) fileLength);
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
