import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
	
 	/**
	 * Get content as string from file.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
  public String getContent(File file) throws IOException {
	BufferedReader reader = new BufferedReader(new FileReader(file));
	StringBuffer buffer = new StringBuffer();
	String row = null;
	while((row = reader.readLine())!=null) {
		buffer.append(row);
	}
	reader.close();
    return buffer.toString();
  }
  
  /**
   * Get content without any unicode characters.
   * @param file
   * @return
   * @throws IOException
   */
  public String getContentWithoutUnicode(File file) throws IOException {
    FileInputStream fis = new FileInputStream(file);
    StringBuffer buffer = new StringBuffer();
    int data;
    while ((data = fis.read()) > 0) {
      if (data < 0x80) {
        buffer.append((char) data);
      }
    }
    fis.close();
    return buffer.toString();
  }
  
  /**
   * Save content to a file.
   * @param file
   * @param content
   * @throws IOException
   */
  public void saveContent(File file, String content) throws IOException {	  
	FileWriter writer = new FileWriter(file);
	writer.write(content);
	writer.close();
  }
}
