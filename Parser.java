import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {

  private final File file;

  public Parser(File file) {
    this.file = file;
  }

  public synchronized String content() throws IOException {
    return readContent(false);
  }

  public synchronized String contentWithoutUnicode() throws IOException {
    return readContent(true);
  }

  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }

  private String readContent(boolean withoutUnicode) throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    if (withoutUnicode) {
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output += (char) data;
        }
      }
    } else {
      while ((data = i.read()) > 0) {
        output += (char) data;
      }
    }
    return output;
  }
}
