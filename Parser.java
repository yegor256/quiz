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
	  StringBuilder output = new StringBuilder();
	  try
	  {
	    FileInputStream i = new FileInputStream(file);
	    
	    int data;
	    while ((data = i.read()) != -1) {
	      output.append( (char) data);
	    }
	  }
	  catch(FileNotFoundException fne)
	  {
		  fne.printStackTrace();
	  }
	  catch(IOException e)
	  {
		  e.printStrackTrace();
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  finally
	  {
		  try
		  {
			  if (i != null)
			  {
				  i.close();
			  }
		  }
		  catch(IOException i)
		  {
			  i.printStackTrace();
		  }
	  }
    return output.toString();
  }
  public String getContentWithoutUnicode() throws IOException 
  {
	  StringBuilder output = new StringBuilder();
	  try
	  {
	    FileInputStream i = new FileInputStream(file);
	    
	    int data;
	    while ((data = i.read()) > -1) {
	      if (data < 0x80) {
	        output.append( (char) data);
	      }
	    }
	  }
	  catch(FileNotFoundException fne)
	  {
		  fne.printStackTrace();
	  }
	  catch(IOException ioe)
	  {
		  ioe.printStackTrace();
	  }
	  catch(Exception e){
		  e.printStackTrace();
	  }
	  finally
	  {
		  try
		  {
			  if (i != null)
			  {
				  i.close();
			  }
		  }
		  catch(IOException i)
		  {
			  i.printlStackTrace();
		  }
		  
	  }
    return output.toString();
  }
  public void saveContent(String content) throws IOException {
	  try{
		  FileOutputStream o = new FileOutputStream(file);
		  if (content != null) && (content.length > 0)
		  {
			    for (int i = 0; i < content.length(); i += 1) {
			      o.write(content.charAt(i));
			    }
		  }
	  }
	  catch(IOException ioe)
	  {
		  ioe.printStackTrace();
	  }
	  finally
	  {
		  try
		  {
			  if (o!= null)
			  {
				  o.close();
			  }
		  }
		  catch(IOException i)
		  {
			  i.printStackTrace();
		  }
	  }
    
  }
}
