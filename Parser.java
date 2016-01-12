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
    FileInputStream input = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = input.read()) > 0) {
      output.append((char) data);
    }
    input.close();
    return output.toString();
  }

  public String getContentWithoutUnicode() throws IOException {
    FileInputStream input = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = input.read()) > 0) {
      if (data < 0x80) {
        output.append((char) data);
      }
    }
    input.close();
    return output.toString();
  }

  public void saveContent(String content) throws IOException {
    FileOutputStream output = new FileOutputStream(file);
    output.write(content.getBytes());
    output.flush();
    output.close();
  }
}
