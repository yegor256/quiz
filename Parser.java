import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;

  /**
   * Make it's a imutabble object.
   */
  public Parser(File file){
	  if(file==null){
		  throw new IllegalArgumentException("File cannot be Null.");
	  }
	  this.file = file;
  }
  public String getContent() throws IOException {
    try(FileInputStream i = new FileInputStream(file)){
      StringBuffer output = new StringBuffer();
      int data;
      while ((data = i.read()) > 0) {
        output.append((char) data);
      }
      return output;
    }
  }
  public String getContentWithoutUnicode() throws IOException {
    try(FileInputStream i = new FileInputStream(file)){
      StringBuffer output = new StringBuffer();
      int data;
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output.append((char) data);
        }
      }
      return output;
    }
  }
  public void saveContent(String content) throws IOException {
    try(FileOutputStream o = new FileOutputStream(file)){
      for (int i = 0; i < content.length(); i += 1) {
        o.write(content.charAt(i));
      }
    }
  }
}
