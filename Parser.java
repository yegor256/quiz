import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
  private File file;

  /**
   * 
   * @param f
   */
  public synchronized void setFile(File f) {
    file = f;
  }

  /**
   * 
   * @return
   */
  public synchronized File getFile() {
    return file;
  }

  /**
   * 
   * @return
   * @throws IOException
   */
  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    i.close();
    return output;
  }

  /**
   * 
   * @return
   * @throws IOException
   */
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    i.close();
    return output;
  }

  /**
   * 
   * @param content
   * @throws IOException
   */
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
    o.close();
  }
}
