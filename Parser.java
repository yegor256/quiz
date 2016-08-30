import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

  private File file;

  public synchronized void setFile(File file) {
    this.file = file;
  }

  public synchronized File getFile() {
    return file;
  }

  public String getContent() throws IOException {
    FileInputStream fileInputStream = new FileInputStream(file);
    StringBuilder outputBuilder = new StringBuilder();
    int data;
    while ((data = fileInputStream.read()) > 0) {
      outputBuilder.append((char) data);
    }
    return outputBuilder.toString();
  }

  public String getContentWithoutUnicode() throws IOException {
    FileInputStream fileInputStream = new FileInputStream(file);
    StringBuilder outputBuilder = new StringBuilder();
    int data;
    while ((data = fileInputStream.read()) > 0) {
      if (data < 0x80) {
        outputBuilder.append((char) data);
      }
    }
    return outputBuilder.toString();
  }

  public void saveContent(String content) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      fileOutputStream.write(content.charAt(i));
    }
  }
}
