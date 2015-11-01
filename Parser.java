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
	String output = "";
	if(file != null && file.exists() && file.canRead())
	{
		FileInputStream i = new FileInputStream(file);
		int data;
		while ((data = i.read()) > 0) {
		  output += (char) data;
		}
		i.close();
	}
    return output;
  }
  public String getContentWithoutUnicode() throws IOException {
	String output = "";
	if(file != null && file.exists() && file.canRead())
	{
		FileInputStream i = new FileInputStream(file);
		int data;
		while ((data = i.read()) > 0) {
		  if (data < 0x80) {
			output += (char) data;
		  }
		}
		i.close();
	}
    return output;
  }
  public void saveContent(String content) throws IOException {
  	if(file != null && file.exists() && file.canWrite())
	{
		FileOutputStream o = new FileOutputStream(file);
		for (int i = 0; i < content.length(); i += 1) {
		  o.write(content.charAt(i));
		}
		o.flush();
		o.close();
	}
  }
}
