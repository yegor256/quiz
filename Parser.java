import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;

  public Parser(File file) {
	  this.file = file;
  }
  public synchronized void setFile(File file) {
    this.file = file;
  }
  public synchronized File getFile() {
    return file;
  }
  public synchronized String getContent() throws IOException {
	  return getContent(true);
  }
  public synchronized String getContentWithoutUnicode() throws IOException {
	  return getContent(false);
  }
  private String getContent(boolean unicode) throws IOException {
	  FileInputStream i = null;
	  try {
		  i = new FileInputStream(file);
		  StringBuffer sb = new StringBuffer();
		  int data;
		  while ((data = i.read()) > 0) {
			  char ch = (char) data;
			  if (!unicode) {
				  if (data < 0x80) {
					  sb.append(ch);
				  }
			  } else {
				  sb.append(ch);
			  }
		  }
		  return sb.toString();
	  } finally {
		  if (i!=null) {
			  try {
				  i.close();
			  } catch (IOException e) {
				  throw e;
			  }
		  }
	  }
  }
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream o = null;
    try {
    	o = new FileOutputStream(file);
    	for (int i = 0; i < content.length(); i += 1) {
	      o.write(content.charAt(i));
	    }
    } finally {
    	if (o!=null) {
    		try {
    			o.close();
    		} catch (IOException e) {
    			throw e;
    		}
    	}
    }
  }
}
