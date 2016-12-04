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
  public void setFile(File f) {
    file = f;
  }
  public File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = "";
    int data;
    while ((data = i.read()) != -1) {
      output.append((char)data);
    }
    return output.toString();
  }
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output.append((char)data);
      }
    }
    return output.toString();
  }
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    o.write(content.getBytes());
  }
}
