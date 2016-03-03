import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser 
{
  private String fileName;

  public Parser(String fileName)
  {
	  this.fileName=fileName;
  }
  
  public String getContent() throws IOException 
  {    
	File file = new File(fileName);
	FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) != -1) 
    {
    	output.append((char) data);
    }
    return output.toString();
  }
  
  public String getContentWithoutUnicode() throws IOException 
  {
	File file = new File(fileName);
	FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) != -1) 
    {
      if (data < 0x80) 
      	output.append((char) data);
    }
    return output.toString();
  }

}
