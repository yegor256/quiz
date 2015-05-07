import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  enum UTF8_ENCODE {YES, NO };
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent(UTF8_ENCODE encode) throws IOException {
    FileInputStream i = new FileInputStream(getFile());
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (encode = UTF8_ENCODE.FALSE || data < 0x80)
      output += (char) data;
    }
    return output;
  }

  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(getFile());
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
