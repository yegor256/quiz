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
    try(
    FileInputStream inputStream = new FileInputStream(file)){
    StringBuilder output = new StringBuilder();
    int data;
    byte[] buffer = new byte[1024];
    while ((data = inputStream.read(buffer)) != -1) {
      output.append(new String(buffer, 0, data).toCharArray());
    }
    return output.toString();
    }
  }
  public synchronized String getContentWithoutUnicode() throws IOException {
   try(
    FileInputStream inputStream = new FileInputStream(file)){
    StringBuilder output = StringBuilder();
    int data;
    byte[] buffer = new byte[1024];
    while ((data = inputStream.read(buffer)) != -1) {
      output.append(new String(buffer, 0, data).toCharArray());
    }
    return output.toString();
    }
  }
  public synchronized void saveContent(String content) throws IOException {
    try(FileOutputStream o = new FileOutputStream(file)){
    o.write(content.getBytes());
    }
  }
}
