

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.StringBuilder;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {
  public static final int UNICODE_CHAR_CODE = 0x80;
  private File file;
  private final ReadWriteLock lock = new ReentrantReadWriteLock();

  public void setFile(File f) {
    lock.writeLock().lock();
    file = f;
    lock.writeLock().unlock();
  }
  public synchronized File getFile() {
    try{
      lock.writeLock().lock();
      return file;
    } finally {
      lock.writeLock().unlock();
    }
  }

  protected String getContent() throws IOException {
    return getContent(false);
  }

  protected String getContent(Boolean withoutUnicode) throws IOException {
    InputStream inputStream = null;
    try {
      lock.readLock().lock();
      if(file == null || !file.exists()){
        throw new IllegalStateException("File was not found");
      }
      inputStream = new FileInputStream(file);//Use Reader
      StringBuilder output = new StringBuilder();
      int data;
      while ((data = inputStream.read()) > 0) {
        if(withoutUnicode != null && withoutUnicode){
          if (data < UNICODE_CHAR_CODE) {
            output.append((char) data);
          }
        } else {
          output.append((char) data);
        }
      }
      return output.toString();
    } finally {
      IOUtils.closeQuietly(inputStream);
      lock.readLock().unlock();
    }
  }

  public String getContentWithoutUnicode() throws IOException {
    return getContent(true);
  }

  public void saveContent(String content) throws IOException {
    OutputStream outputStream = null;
    try {
      lock.writeLock().lock();
      if(file == null || !file.exists()){
        throw new IllegalStateException("File was not found");
      }
      outputStream = new FileOutputStream(file);//Use Writer
      for (int i = 0; i < content.length(); i ++) {
        outputStream.write(content.charAt(i));
      }
    } finally {
      IOUtils.closeQuietly(outputStream);
      lock.writeLock().unlock();
    }
  }
}

