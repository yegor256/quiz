import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  public synchronized void setFile(File file) {
    this.file = file;
  }
  public synchronized File getFile() {
    return this.file;
  }
  public String getContent() throws IOException {
	return getContent(false);
  }
  public String getContentWithoutUnicode() throws IOException {
	return getContent(true);
  }
  private static int UNICODECHAR = 0x80;
  private static int ENDOFFILE = -1;
  private static String EMPTYSTRING = "";
  protected String getContent(boolean withoutUnicode) throws IOException{
	FileInputStream inputStream = new FileInputStream(file);
	String output = EMPTYSTRING;
	int data;
	while ((data = inputStream.read()) != ENDOFFILE) {
	  if(withoutUnicode){
		if (data >= UNICODECHAR) {
	      continue;
	    }
	  }
	  output += (char) data;
	}
	return output;
  }
  
  public void saveContent(String content) throws IOException {
    FileOutputStream outputStream = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i++) {
      outputStream.write(content.charAt(i));
    }
  }
}
