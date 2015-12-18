import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.StringBuilder;
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
    if(file == null) return;
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      output.append(String.valueOf(data));
    }
    if(i != null) i.close();
    return output.toString();
  }
  public String getContentWithoutUnicode() throws IOException {
    if(file == null) return;
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output.append(String.valueOf(data));
      }
    }
    
    return output.toString();
  }
  public void saveContent(String content) throws IOException {
    if (file == null) return;
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
    if(o != null) o.close();
  }
}
