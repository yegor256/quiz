import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 * TODO: Refactor to take file in constructor and remove setFile if possible
 */
public class Parser {
  private File file;
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public synchronized String getContent() throws IOException {
    InputStream i = new BufferedInputStream(new FileInputStream(file));
    String output = "";
    int data;
    while ((data = i.read()) != -1) {
      output += (char) data;
    }
    i.close();
    return output;
  }
  /*
   * TODO: Naming of this function is misleading. Unicode is about encoding.
   * This method is returning only 7-bit ascii *bytes*.
   * However, it fails to detect multi-byte non-ascii *characters*
   */
  public synchronized String getContentWithoutUnicode() throws IOException {
    InputStream i = new BufferedInputStream(new FileInputStream(file));
    String output = "";
    int data;
    while ((data = i.read()) != -1) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    i.close();
    return output;
  }
  public synchronized void saveContent(String content) throws IOException {
    OutputStream o = new BufferedOutputStream(new FileOutputStream(file));
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
    o.close();
  }
}
