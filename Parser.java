import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
	StringBuilder output = new StringBuilder();
	if(file != null) {
	    FileInputStream fis = new FileInputStream(file);
	    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis));
	    String data;
	    while ((data = bufferReader.readLine()) != null) {
	      output.append(data);
	    }
	}
    return output.toString();
  }
  public String getContentWithoutUnicode() throws IOException {
	StringBuilder output = new StringBuilder();
	if(file != null) {
	    FileInputStream i = new FileInputStream(file);
	    int data;
	    while ((data = i.read()) > 0) {
	      if (data < 0x80) {
	        output.append(data);
	      }
	    }
	}
    return output.toString();
  }
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    o.write(content.getBytes());
    /*for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }*/
  }
}
