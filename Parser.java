import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * This class is thread safe.
 */
public class Parser {
  public String getContent(File file) throws IOException {
    InputStream i = new BufferedInputStream(new FileInputStream(file));
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
        output.append((char) data);
    }
    return output.toString();
  }
  public String getContentWithoutUnicode(File file) throws IOException {
    InputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output.append((char) data);
      }
    }
    return output.toString();
  }
  public void saveContent(String content, File file) throws IOException {
    OutputStream o = new FileOutputStream(file);
      o.write(content.getBytes());
  }
}
