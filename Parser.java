import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private volatile File file;
  
  private final Object LOCK = new Object();
  
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent() throws IOException {
      
   FileInputStream i;
    synchronized (LOCK)
    {
        i = new FileInputStream(file);  
    }
   
    try
    {
    
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = i.read()) > 0) {
          output.append((char) data);
        }
        return output.toString();
        
    
    }
    finally
    {
        i.close();
    }
  }
  public String getContentWithoutUnicode() throws IOException {
     
      FileInputStream i;
      synchronized (LOCK)
      {
          i = new FileInputStream(file);  
      }
     
      try
      {
      
          StringBuilder output = new StringBuilder();
          int data;
          while ((data = i.read()) > 0) {
              if (data < 0x80) 
                  output.append((char) data);
          }
          return output.toString();
          
      
      }
      finally
      {
          i.close();
      }
  }
  public void saveContent(String content) throws IOException {
      
    synchronized (LOCK)
    {
        FileOutputStream o = new FileOutputStream(file);
        try
        {
            for (int i = 0; i < content.length(); i += 1) {
              o.write(content.charAt(i));
            }
        }
        finally
        {
            o.close();
        }
    }
  }
}
