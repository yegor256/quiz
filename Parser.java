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

  public synchronized void setFile(File f) {
    file = f;
  }

  public synchronized File getFile() {
    return file;
  }

  // make sure that the caller would handle the IOException.
  // Otherwise use a try-catch statement to handle it here.
  public synchronized String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
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
  public synchronized String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
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
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    // if file does not exist, then create it
    if (!file.exists()) {
      file.createNewFile();
    }
    // get the content in bytes
    byte[] contentInBytes = content.getBytes();
    o.write(contentInBytes);
    o.flush();
    o.close();
  }

}
