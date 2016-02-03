import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private static int MAX_INT = 0x80;
  private File file;
  public synchronized void setFile(File file) {
    this.file = file;
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    FileInputStream inputStream = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = inputStream.read()) > 0) {
      output += (char) data;
    }
    inputStream.close();
    return output;
  }
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream inputStream = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = inputStream.read()) > 0) {
      if (data < MAX_INT) {
        output += (char) data;
      }
    }
    inputStream.close();
    return output;
  }
  public void saveContent(String content) throws IOException {
    FileOutputStream outputStream = new FileOutputStream(file);
    for (int index = 0; index < content.length(); index += 1) {
    	outputStream.write(content.charAt(index));
    }
    outputStream.close();
  }
}
