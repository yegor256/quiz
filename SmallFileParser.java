import java.io.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class SmallFileParser {
  private final String fileName;
  private final SmallDataReader reader = new SmallDataReader();
  private final SmallDataWriter writer = new SmallDataWriter();
  private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

  public SmallFileParser(String fileName) {
    this.fileName = fileName;
  }

  public String readUnicodeContent() throws IOException {
    readWriteLock.readLock().lock();

    try {
      return reader.readUnicodeContent(new FileReader(fileName));
    } finally {
      readWriteLock.readLock().unlock();
    }
  }

  public String readAsciiContent() throws IOException {
    readWriteLock.readLock().lock();

    try {
      return reader.readAsciiContent(new FileReader(fileName));
    } finally {
      readWriteLock.readLock().unlock();
    }
  }

  public void writeContent(String content) throws IOException {
    readWriteLock.writeLock().lock();

    try {
      writer.writeContent(new FileWriter(fileName), content);
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }
}