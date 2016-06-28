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

  public String getContent(boolean withUnicode) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = fileInputStream.read()) > 0) {
	  if (withUnicode || data < 0x80){
      	output += (char) data;
	  }
    }
	fileInputStream.close();
    return output;
  }

  public String getContent() throws IOException{
	return getContent(true);
  }

  public String getContentWithoutUnicode() throws IOException {
	return getContent(false);
  }

  public void saveContent(String content) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(file);
	byte contentBytes[] = content.getBytes();
	fileOutputStream.write(contentBytes);
	fileOutputStream.close();
  }
}
