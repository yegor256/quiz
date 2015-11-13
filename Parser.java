import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  public synchronized void setFile(File f) {
    this.file = f;
  }
  public synchronized File getFile() {
    return this.file;
  }
  public synchronized String getContent() throws IOException {
    FileInputStream i = new FileInputStream(this.file);
    String output = "";
    int data = 0;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    return output;
  }
  public synchronized String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(this.file);
    String output = "";
    int data = 0;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    return output;
  }
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(this.file);
    for (int i = 0; i < content.length(); i++) {
      o.write(content.charAt(i));
    }
  }
}
