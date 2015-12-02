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
  public String getContent() throws IOException {
    synchronized (file) {
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded);
    }
  }
  public String getContentWithoutUnicode() throws IOException {
    synchronized (file) {
      FileInputStream i = new FileInputStream(file);
      StringBuilder output = new StringBuilder();
      int data;
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output.append((char) data);
        }
      }
      i.close();
      return output.toString();
    }
  }
  public void saveContent(String content) throws IOException {
    if (content == null) return;
    synchronized(file) {
      FileOutputStream o = new FileOutputStream(file);
      o.write(content.getBytes());
    }
  }
}
