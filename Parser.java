package quiz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
    Path path = file.toPath();
    byte[] bytes = Files.readAllBytes(path);
    return new String(bytes);
  }
  
  public String getContentWithoutUnicode() throws IOException {
    Path path = file.toPath();
    byte[] bytes = Files.readAllBytes(path);
    String output = "";
    
    for(int i = 0; i < bytes.length; i++){
      if (bytes[i] < 0x80) {
        output += (char) bytes[i];
      }
    }
    return output;
  }
  
  public void saveContent(String content) throws IOException {
    OutputStream output = new FileOutputStream(file);
    output.write(content.getBytes());
    output.flush();
    output.close();
  }
  
}
