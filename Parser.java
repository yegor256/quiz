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
  private String getContentByMaxVal(int max_val) throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < max_val) {
        output += (char) data;
      }
    }
    return output;
  }

  public String getContent() throws IOException {
      return getContentByMaxVal(Integer.MAX_VALUE);
  }
  public String getContentWithoutUnicode() throws IOException {
      return getContentByMaxVal(0x80);
    }
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
