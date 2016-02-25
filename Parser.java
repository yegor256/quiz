import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  
  // Declare file name for use in
  // FileInputStream and FileOutputStream
  
  public String parsedFile = "parsed.txt";
  
  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(parsedFile);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    return output;
  }
  
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(parsedFile);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    return output;
  }
  
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(parsedFile);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
