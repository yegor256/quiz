import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * TODO not the best javadoc
 * This class is thread safe.
 *  
 */
public class Parser {
  private File file;
  
  // It should not be possible to use parser without file
  // setter was good but user could call getContent method without 
  // calling setter
  public Parser(File f) {
    file = f;
  }
  
  public synchronized File getFile() {
    return file;
  }
  
  //TODO it's better to use try catch finally or try-with-resources
  // stream must be closed 
  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = ""; //TODO can be a constant
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    return output;
  }
  
  //TODO it's better to use try catch finally or try-with-resources
  // stream must be closed 
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) { //TODO must be a constant
        output += (char) data;
      }
    }
    return output;
  }
  
   //TODO it's better to use try catch finally or try-with-resources
  // stream must be closed 
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
