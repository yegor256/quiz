import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  
  /** Instead of creating this separate method, we should have a parameterized constructor.
  * This makes sure that user always provides a file and makes the implemetation more sensible.
  * */
  // public synchronized void setFile(File f) {
  //   file = f;
  // }
  
  public Parser(File f){
    file = f;
  }
  
  public synchronized File getFile() {
    return file;
  }
  
  // 1. We need to close the FileInputStream
  // 2. Also I am not entirely sure but if we are just printing all content,
  // we do not need to go character by character. Using condition like while((line = i.readLine()) != null)
  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    i.close();
    return output;
  }
  
  // We need to close the FileInputStream
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    i.close();
    return output;
  }
  
  // We need to close the FileOutputStream
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
    o.close();
  }
}
