import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
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
  public synchronized String getContent() throws IOException {
	  StringBuilder output = new StringBuilder();
	  BufferedReader reader = new BufferedReader(new FileReader(file));
      char[] buf = new char[1024];
      int numRead = 0;
      try {
    	while((numRead = reader.read(buf)) != -1){
    	  output.append(buf, 0, numRead);
      }
    }
    finally {
   		reader.close();
	}
    return output.toString();
  }
  public synchronized String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    try {
    	while ((data = i.read()) != -1) {
    		if (data < 0x80) {
    			output.append((char) data);
    		}
    	}
    }
      finally {
    	  i.close();
      }
    return output.toString();
  }
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    try {
    	for (int i = 0; i < content.length(); i += 1) {
    		o.write(content.charAt(i));
    	}
    } finally {
    	o.close();
    }
    
  }
}
