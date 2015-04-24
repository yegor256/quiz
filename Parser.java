import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
  public synchronized String getContent() throws IOException {
    if(!(file != null && file.isFile())) throw new IOException("File error");
    
    BufferedInputStream i = new BufferedInputStream (new FileInputStream(file));
    StringBuilder output = new StringBuilder();
    int data = 0;
    while ((data = i.read()) > 0) {
      output.append((char) data);
    }
    
    i.close();
    return output.toString();
  }
  public synchronized String getContentWithoutUnicode() throws IOException {
    if(!(file != null && file.isFile())) throw new IOException("File error");
    
    BufferedInputStream i = new BufferedInputStream(new FileInputStream(file));
    StringBuilder output = new StringBuilder();
    int data = 0;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output.append((char) data);
      }
    }
    
    i.close();
    return output.toString();
  }
  public synchronized void saveContent(String content) throws IOException {
    if(!(file != null && file.isFile())) throw new IOException("File error");
    
    BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file));
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
    
    o.close();
  }
}
