import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 * Constructor is missing to initialize the file
 * the class is not really thread safe since that write and read content can happen in the same time
 * use try catch instate of throws
 * 
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
    // test if the file exist
    FileInputStream i = new FileInputStream(file);
    // use StringBuffer instate of String
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    return output;
  }
  public String getContentWithoutUnicode() throws IOException {
    // test if the file exist
    FileInputStream i = new FileInputStream(file);
    // use StringBuffer instate of String
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    // i must be closed
    return output;
  }
  public void saveContent(String content) throws IOException {
    // test if the file exist
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
    //o.flush() and o.close() are missing
  }
}
