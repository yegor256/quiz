import java.io.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * This class is thread safe.
 */
public class Parser {
  public final File file;
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  public Parser(final File f) {
    file = f;
  }
  public String getContent() {
    readWriteLock.readLock().lock();
    try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
      StringBuilder output = new StringBuilder();
      int data;
      while ((data = i.read()) > 0) {
        output.append((char) data);
      }
      return output.toString();
    } catch (final IOException ex) {
      throw new IllegalStateException(ex);
    } finally {
      readWriteLock.readLock().unlock();
    }
  }
  public String getContentWithoutUnicode() {
    readWriteLock.readLock().lock();
    try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
      StringBuilder output = new StringBuilder();
      int data;
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output.append((char) data);
        }
      }
      return output.toString();
    } catch (final FileNotFoundException ex) {
      throw new IllegalArgumentException(ex);
    } catch (final IOException ex) {
      throw new IllegalStateException(ex);
    } finally {
      readWriteLock.readLock().unlock();
    }
  }
  public void saveContent(final String content) {
    readWriteLock.writeLock().lock();
    try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
      for (int i = 0; i < content.length(); i += 1) {
        o.write(content.charAt(i));
      }
    } catch (final FileNotFoundException ex) {
      throw new IllegalArgumentException(ex);
    } catch (final IOException ex) {
      throw new IllegalStateException(ex);
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }
}
