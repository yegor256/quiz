import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {

  /** variable to hold the file object */
  private File file;
  
  /**
   * Method to set the file object
   * @param f
   */
  public synchronized void setFile(File f) {
    file = f;
  }
  
  /**
   * Method to get the file object
   * @return
   */
  public synchronized File getFile() {
    return file;
  }
  
  /**
   * Method to get the content as string
   * @return string
   * @throws IOException
   */
  public String getContent() throws IOException {	
    return getContent(true);
  }
  
  /**
   * Method to get the content withoug UniCode
   * @return string
   * @throws IOException
   */
  public String getContentWithoutUnicode() throws IOException {
    return getContent(false);
  }
  
  /**
   * Method get the content based on the unicode flag
   * @param uniCodeFlag
   * @return
   * @throws IOException
   */
  private String getContent(boolean uniCodeFlag) throws IOException {
		FileInputStream fileInputStream = null;
		String output = "";
	    try {
		  fileInputStream = new FileInputStream(file);      
	      int data;
	      while ((data = fileInputStream.read()) > 0) {
	    	if (uniCodeFlag) {
	    	  output += (char) data;
	    	} else if (data < 0x80) {
	          output += (char) data;
	        }
	      } 
	    } catch (IOException e) {
			//TODO log the meesage
			throw e;
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
		return output;
	}
  
  /**
   * Method to save the content to file
   * @param content
   * @throws IOException
   */
  public void saveContent(String content) throws IOException {
	FileOutputStream o = null;
	try {
	    o = new FileOutputStream(file);
	    for (int i = 0; i < content.length(); i += 1) {
	      o.write(content.charAt(i));
	    }
	} catch (IOException e) {
		//TODO log the meesage
		throw e;
	} finally {
		if (o != null) {
			o.close();
		}
	}
  }
}
