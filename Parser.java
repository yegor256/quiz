import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  private FileInputStream inputStream;
  String output = "";
  public synchronized void setFile(File f) throws IOException {
    file = f;
    inputStream = new FileInputStream(file);
  }
  public synchronized File getFile() {
    return file;
  }
  public synchronized String getContent() throws IOException {
    int data = inputStream.read();
    while (data > 0) {
      output += (char) data;
    }
    return output;
  }
  public synchronized String getContentWithoutUnicode() throws IOException {
    int data = inputStream.read();
    while (data > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    return output;
  }
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
