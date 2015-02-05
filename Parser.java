import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  
  protected interface Condition {
		public boolean check(int input);
	}
  
  private String getContent(File file, Condition condition) {
    FileInputStream i = null;
    try {
      i = new FileInputStream(file);
      StringBuffer output = new StringBuffer();
      int data;
      while ((data = i.read()) > 0) {
        if (condition == null || condition.check(data)) {
          output.append((char) data);
        }
      }
      return output.toString();
    } finally {
      if (i != null) { try {i.close();} catch (IOException e) {} }
    }
  }
  
  public String getContent(File file) throws IOException {
    return getContent(file, null);
    
  }
  public String getContentWithoutUnicode(File file) throws IOException {
    return getContent(file, new Condition(){
      public boolean check(int input) {
        return input < 0x80;
      }
    });
  }
  public void  saveContent(String content, File file) throws IOException {
    FileOutputStream o = null;
    try {
      o = new FileOutputStream(file);
      for (int i = 0; i < content.length(); i += 1) {
        o.write(content.charAt(i));
      }
    } finally {
      if (o != null) { try {o.close();} catch (IOException e) {} }
    }
  }
}
