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
  
  public void saveContent(String content) throws IOException {
	    FileOutputStream o = new FileOutputStream(file);
	    int length = content.length();
	    for (int i = 0; i < length; i += 1) {
	      o.write(content.charAt(i));
	    }
	  }
  
  
  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    return output;
  }
  
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    return output;
  }
 
}
