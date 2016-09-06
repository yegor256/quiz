import java.io.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A thread-safe helper class for reading a files content as text,
 * or writing text into it.
 */
public class FileReadWriteHelper {

  public static final int DEFAULT_BUFFER_SIZE = 1024 * 16;

  private final File file;
  private ReadWriteLock readWriteLock;

  public FileReadWriteHelper(File file) {
    this.file = file;
    this.readWriteLock = new ReentrantReadWriteLock();
  }

  /**
   * Reads content of corresponding file into string.
   * an I/O error occurs, if file does not exits.
   * stream is reached.
   *
   * @return an string representing content of the file
   *
   * @throws IOException if I/O error occurs
   */
  public String read() throws IOException {
    return read(false);
  }

  /**
   * Reads content of corresponding file into string.
   * It will skip Non-ASCII characters.
   * an I/O error occurs, if file does not exist.
   * stream is reached.
   *
   * @return an string representing content of the file
   *
   * @throws IOException if I/O error occurs
   */
  public String getContentWithoutUnicode() throws IOException {
    return read(true);
  }

  protected String read(boolean onlyAscii) throws IOException {
    readWriteLock.readLock().lock();
    FileInputStream fileInputStream = new FileInputStream(file);
    StringBuilder result = new StringBuilder();
    try {
      Reader reader = new BufferedReader(new InputStreamReader(fileInputStream));
      char[] buffer = new char[DEFAULT_BUFFER_SIZE];
      int read;

      // we can also put this {@code if} inside the following {@code while},
      // but this way is more efficient, since {@code if} check would runs just once.
      if (onlyAscii) {
        while ((reader.read(buffer, 0, buffer.length)) > 0) {
            result.append(String.valueOf(buffer).replaceAll("[^ -~]", ""));
        }
      } else {
        while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
          result.append(buffer, 0, read);
        }
      }

    } finally {
      fileInputStream.close();
    }
    readWriteLock.readLock().unlock();
    return result.toString();
  }

  /**
   * Write an string into the corresponding file.
   * It will create a new file if the file does not exist.
   *
   * @param content that we want to write to the file.
   *
   * @throws IOException if I/O error occurs
   */
  public void write(String content) throws IOException {
    readWriteLock.writeLock().lock();
    Writer writer = new BufferedWriter(new FileWriter(file));
    try {
      writer.write(content);
    } finally {
      writer.close();
    }
    readWriteLock.writeLock().unlock();
  }
}
