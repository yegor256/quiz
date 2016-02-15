import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.StringBuilder;

/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  /*  The lock field is declared private final Object, so that is inaccessible to callers that are outside the class's scope */
  private final Object lock = new Object();

  public setFile(File f) {
    synchronized (lock) {
      this.file = f;
    }
  }

  public File getFile() {
    return this.file;
  }

  // make sure that the caller would handle the IOException.
  // Otherwise use a try-catch statement to handle it here.
  public String getContent() throws IOException {
    // let's mark as synchronized only the critical section. Locks on the private object.
    synchronized (lock) {
      FileInputStream i = new FileInputStream(file);
    }
    // let's use a StringBuilder as it is faster
    StringBuilder builder = new StringBuilder();
    int data;
    while ((data = i.read()) != -1) {
      builder.append((char)data);
    }
    i.close();
    return sb.toString();
  }

  // make sure that the caller would handle the IOException.
  // Otherwise use a try-catch statement to handle it here.
  public String getContentWithoutUnicode() throws IOException {
    // let's mark as synchronized only the critical section. Locks on the private object.
    synchronized (lock) {
      FileInputStream i = new FileInputStream(file);
    }
    // let's use a StringBuilder as it is faster
    StringBuilder builder = new StringBuilder();
    int data;
    while ((data = i.read()) != -1) {
      if (data < 0x80) {
        builder.append((char)data);
      }
    }
    i.close();
    return sb.toString();
  }

  // make sure that the caller would handle the IOException.
  // Otherwise use a try-catch statement to handle it here.
  public void saveContent(String content) throws IOException {
    // let's mark as synchronized only the critical section. Locks on the private object.
    synchronized (lock) {
      // if file does not exist, then create it
      if (!file.exists()) {
        file.createNewFile();
      }
      FileOutputStream o = new FileOutputStream(file);
    }
    // get the content in bytes
    byte[] contentInBytes = content.getBytes();
    o.write(contentInBytes);
    o.flush();
    o.close();
  }

}
