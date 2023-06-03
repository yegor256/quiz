import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
  private final File file;
  private static final int FIRST_UNICODE_CHAR = 0x80;
  public Parser (File f) {
    file = f;
  }
  public File getFile() {
    return file;
  }
  private String readContent(boolean supportUnicode) throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      if (supportUnicode || data < FIRST_UNICODE_CHAR) {
        output.append((char)data);
      }
    }
    i.close();
    return output.toString();
  }
  public String getContent() throws IOException {
    return readContent(true);
  }
  public String getContentWithoutUnicode() throws IOException {
    return readContent(false);
  }
  public void saveContent(String content) {
    FileOutputStream o = new FileOutputStream(file);
    try {
      for (int i = 0; i < content.length(); i += 1) {
        o.write(content.charAt(i));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    o.close();
  }
}
