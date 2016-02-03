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
  
  /**
   * Parse file content and save.
   * 
   * @param withoutUnicode Set to true to skip Unicode
   * @throws IOException
   */
  public void parse(boolean withoutUnicode) throws IOException{
	  String content;
	  
	  if(withoutUnicode){
		  content = getContentWithoutUnicode();
	  }
	  else{
		  content = getContent();
	  }
	  
	  saveContent(content);
  }
  
  private void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
    }
    o.close();
  }
  
  private String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    i.close();

    return output;
  }
  
  private String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }    
    i.close();

    return output;
  }
}
