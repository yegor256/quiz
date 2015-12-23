import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
		  return readFileWithUnicode(true);
	  } 
	  public String getContentWithoutUnicode() throws IOException {
		  return readFileWithUnicode(false);
	  }
	  
	  public String readFileWithUnicode(boolean isUnicodedDataParsed) throws IOException {
		FileInputStream fis = null;
		try {
		    fis = new FileInputStream(file);
		    String output = "";
		    int data;
		    while ((data = fis.read()) > 0) {
		        if (data < 0x80 || isUnicodedDataParsed) {
		            output += (char) data;
		          }
		    }
		    return output;
		} finally {
			if (fis!=null) {
				fis.close();  
			}
		}
	  } 
	  public void saveContent(String content) throws IOException {
		FileOutputStream fos = null;
		try {
	    fos = new FileOutputStream(file);
	    for (int i = 0; i < content.length(); i += 1) {
	      fos.write(content.charAt(i));
	    }
	    
		} finally {
			if (fos!=null) {
				fos.close();  
			}
		}
	  }
	}