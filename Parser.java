import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.

I avoid exceptions like the plauge. Not only do they break program execution but they are expensive: http://stackoverflow.com/a/299315/29505

 */
public class Parser {
  private File file;
  public final int UNICODE_BOUNDARY = 0x80;
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  
   public String getContent() throws IOException 
   {
   	   return getContent(-1); 
   }
  
  public String getContent(int upperBound) throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
		if((data < upperBound) && (upperBound != -1))
		{
      	  output += (char) data;
	  	}
    }
    return output;
  }
  
  public String getContentWithoutUnicode() throws IOException {
	  return getContent(UNICODE_BOUNDARY);
  }
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
