import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedInputStream;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  public synchronized void setFile(File f) {
    if (f != null && f.isFile() && f.canWrite())
      file = f;
    else 
      throw new IllegalArgumentException();
  }
  
  public synchronized File getFile() {
    return file;
  }
  
  public synchronized String getContent() throws IOException {
    return getContent(true);
  }
  
  public synchronized String getContentWithoutUnicode() throws IOException {
    return getContent(false);
  }
  
  private synchronized String getContent(boolean withUnicode) throws IOException {
    try (BufferedInputStream br = new BufferedInputStream(new FileInputStream(file))) {
      StringBuffer sb = new StringBuffer();
      int data;
      while ((data = i.read()) != -1){
        if (withUnicode){
            sb.append((char) data);
        } else if (data < 0x80) {
          sb.append((char) data);
        }
      }
      
      return sb.toString();
    }
  }
  
  public synchronized void saveContent(String content) throws IOException {
    try (Writer fw = new FileWriter(file)) {
      fw.write(content);
    }
  }
}
