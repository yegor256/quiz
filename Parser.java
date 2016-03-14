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
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(getFile());
    String output = "";
    int data;
    while ((data = i.read()) != -1) {
      output += (char) data;
    }
    i.close()
    return output;
  }
  
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(getFile());
    String output = "";
    int data;
    while ((data = i.read()) !=-1) {
      if (data <= 0x007f) {
        output += (char) data;
      }
    }
    i.close();
    return output;
  }
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(getFile());
    o.write(content.getBytes));
    o.close()
  }
}
