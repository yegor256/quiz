import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

/**
 * This class is thread safe.
 * 
 * crimaniak: We have getContentWithoutUnicode() method so I assume this class operates text files in UTF-8 encoding
 */
public class Parser 
{
	  private File file;
	  
	  public Parser(File f) 
	  {
		  file = f;
	  }
	  
	  // Pointer operations are atomic.
	  // I don't like this interface. It's better to remove it.
	  public void setFile(File f) 
	  {
		  file = f;
	  }
	  public File getFile() 
	  {
		  return file;
	  }
	  

	  public String getContent() throws IOException 
	  {
		  return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
	  }
	  
	  /**
	   * Reads file into string, filter out non-ASCII characters.
	   * Quick and dirty solution. More memory-efficient will be 
	   * additional class NonAsciiFilterStream extends FilterInputStream {...}
	   * @throws IOException
	   */
	  public String getContentWithoutUnicode() throws IOException 
	  {
		  return FileUtils.readFileToString(file, StandardCharsets.UTF_8).replaceAll("[^\\u0000-\\u0079]+", "");
	  }
	  
	  public void saveContent(String content) throws IOException 
	  {
		  FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
	  }
}
