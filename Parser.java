import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

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
  
    // read and write operations should be synchronized in multithreading context.
  public synchronized String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
	// use StringBuilder instead of string 
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      output.append((char)data);
    }
	// close operation necessary to remove memory leak.
    i.close();
    return output.toString();
  }
  
    // read and write operations should be synchronized in multithreading context.
  public synchronized String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
	
	// to remove unicode characters we use Reader class as it uses default character encoding.
    Reader r = new InputStreamReader(i);
	
	// use StringBuilder instead of string 
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
        output.append((char)data);
      
    }
	// close operation necessary to remove memory leak.
    r.close();
    return output.toString();
  }
  
  // read and write operations should be synchronized in multithreading context.
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
	// close operation necessary to remove memory leak.
    o.close();
  }

}
