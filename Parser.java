import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
/**
 * This class is thread safe.
 */
public class Parser {
  private volatile File file;
  
  public synchronized void setFile(File f) {
    file = f;
  }
  
  public  File getFile() {
    return file;
  }
  
  /**
   * Returns contents of the file as String 
   * 
   * @return
   * @throws IOException
   */
  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    
    try {
    StringWriter stringWriter = new StringWriter();
    //Configurable chunk 10 kbs here
    byte[] buffer = new byte[1024*10];
   
    while (i.read(buffer) > 0) {
    	stringWriter.append(new String(buffer));
     }
    
    return stringWriter.toString();
    
    } finally {
    	i.close();
    }
    
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
  
  /**
   * Save the content in a file
   * 
   * @param content
   * @throws IOException
   */
  public void saveContent(String content) throws IOException {
	
    FileOutputStream o = new FileOutputStream(file);
    
    try {
    	o.write(content.getBytes());
    } finally {
    	o.close();
    }
  }
}
