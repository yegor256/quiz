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
  
  public String getContent() throws IOException {
    return prepareContent(false);
  }
  
  public String getContentWithoutUnicode() throws IOException {
    return prepareContent(true);
  }
  
  private String prepareContent(boolean withoutUnicode) throws IOException {
    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = bufferedInputStream.read()) > 0) {
      if (withoutUnicode && !(data < 0x80)) continue;
      output.append((char) data);
    }
    bufferedInputStream.close();
    return output.toString();
  }
  
  public void saveContent(String content) throws IOException {
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
    for (int i = 0; i < content.length(); i += 1) {
      bufferedOutputStream.write(content.charAt(i));
    }
    bufferedOutputStream.close();
  }
}
