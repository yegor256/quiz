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
  public String getContent() throws IOException {
  
    return getContentWithFlag(false);
  }
  public String getContentWithoutUnicode() throws IOException {
    
    return getContentWithFlag(true);
  }
  public String getContentWithFlag(boolean flag) throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuffer output = new StringBuffer();
    int data;
    while ((data = i.read()) > 0) {
      if (flag && data < 0x80) {
        continue;
       }
      output.append((char) data);
    }
    return output.toString();
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
  }
}
