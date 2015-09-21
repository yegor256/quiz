import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
	// Following code also can be replaced by FileReader or Scanner class
	// to speed up the reading process.  
	// but here I only will close the FileInputStream object after
	// after reading.   
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    i.close();
    return output;
  }
  public String getContentWithoutUnicode() throws IOException {
	// Following code also can be replaced by FileReader or Scanner class
	// to speed up the reading process.  
	// but here I only will close the FileInputStream object after
	// after reading.  
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
  public void saveContent(String content) throws IOException {
	    // code replaced with FileWriter this will make 
	    // the process fast, as writing character by character is bit slow.
	    FileWriter fw = new FileWriter( file.getAbsoluteFile( ) );
	    BufferedWriter bw = new BufferedWriter( fw );
	    bw.write(content);
	    bw.close( );
  }
}
