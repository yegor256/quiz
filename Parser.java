
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser 
{
  private File file;
  
  public synchronized void setFile(File f) 
  {
    file = f;
  }
  
  public synchronized File getFile() 
  {
    return file;
  }
  
  public synchronized String getContent() throws IOException 
  {
    FileInputStream i = new FileInputStream(file);
  
    String output = "";
    
    int data;
    
    while ((data = i.read()) > 0) 
    {
      output += (char) data;
    }
    
    return output;
  }
  
  public synchronized String getContentWithoutUnicode() throws IOException 
  {
    FileInputStream i    = null;
    StringBuilder output = null;
    
    try
    {
    	i = new FileInputStream(file);

    	output = new StringBuilder();
        
        int data;
      
        while ((data = i.read()) > 0) 
        {
          if (data < 0x80) 
          {
            output.append((char) data);
          }
        }
    }
    catch(IOException ie)
    {
    	//Do something
    }
    finally
    {
    	i.close();
    }
    
    return output.toString();
  }
  
  public void saveContent(String content) throws IOException 
  {
	  BufferedWriter writer = null;
	  
	  try
	  {
		  writer = new BufferedWriter( new FileWriter( file));
		  writer.write( content);
	  }
	  catch(IOException ie)
	  {
		  //do something here
	  }
	  finally
	  {
		  writer.close();
	  }
  }
}
