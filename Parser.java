import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * @author smarichal
 * This class can read and write content from or to a text file.
 * This class is thread safe.
 */
public class Parser {
	
  private File file;
  
  /**
   * 
   * @param f. Set the file to parse
   */
  public synchronized void setFile(File f) {
    this.file = f;
  }
  
  /**
   * 
   * @return the File to parse
   */
  public synchronized File getFile() {
    return this.file;
  }
  
  /**
   * 
   * @param includeUnicode If true then the result string includes unicode characters
   * @return A string representing the content of the file 
   * @throws IOException
   */
  public synchronized String getContent(boolean includeUnicode) throws IOException {
	  	FileInputStream inputStream = null;
	try{
		inputStream = new FileInputStream(this.file);
	
	    String output = "";
	    int data;
	    while ((data = inputStream.read()) > 0) {
	    	if (includeUnicode && data < 0x80){
	    		output += (char) data;
	    	}
	    }
	    inputStream.close();
	    return output;
	}finally{
		if(inputStream!=null){
			inputStream.close();
		}
	}
  }
    
  /**
   * 
   * @param content This is the content to save in the file. If any previous content exist in the file 
   * it will be overwritten. 
   * @throws IOException
   */
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream outputStream = null;
    try{
    	outputStream = new FileOutputStream(this.file);
    	for (int i = 0; i < content.length(); i += 1) {
    		outputStream.write(content.charAt(i));
    	}
    	outputStream.close();
    }finally{
    	if(outputStream!=null){
    		outputStream.close();
    	}
    }
  }
  
}
