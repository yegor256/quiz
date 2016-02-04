import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Parser {
  private volatile File file;
  public void setFile(File f) {
    file = f;
  }
  public File getFile() {
    return file;
  }
  public String getContent(boolean withUnicode) throws IOException {
	if(file != null) {
	    try(final FileInputStream i = new FileInputStream(file)) {
		    StringBuilder output = new StringBuilder();
		    int data;
		    while ((data = i.read()) > 0) {
		    	if (withUnicode || data < 0x80) {
		    		output.append((char) data);
		    	}
		    }
		    
		    return output.toString();
	    }
	}
	
	throw new IllegalArgumentException("File is not set");
  }
  public String getContentWithoutUnicode() throws IOException {
	  return getContent(false);
  }
  public void saveContent(String content) throws IOException {
    try(FileOutputStream o = new FileOutputStream(file)) {
    	o.write(content.getBytes());
    }
  }
}
