import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private volatile File file;
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public synchronized String getContent() throws IOException {
    StringBuilder sb = new StringBuilder();
    try (FileInputStream i = new FileInputStream(file)) {
        int data;
        while ((data = i.read()) > 0) {
            sb.append((char) data);
        }
    }
    return sb.toString();
  }
  public synchronized String getContentWithoutUnicode() throws IOException {
    StringBuilder sb = new StringBuilder();
    try (FileInputStream i = new FileInputStream(file)) {
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                sb.append((char) data);
            }
        }
    }
    return sb.toString();
  }
  public synchronized void saveContent(String content) throws IOException {
    try (FileOutputStream o = new FileOutputStream(file)) {
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
  }
}
