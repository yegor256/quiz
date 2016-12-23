import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	FileInputStream i;
	try {
		i = new FileInputStream(file);
		String output = "";
		int data;
		while ((data = i.read()) > 0) {
			output += (char) data;
		}
		return output;
	} catch (FileNotFoundException e) {
		// handle the case when file cannot be found
		// instead of swallowing it in IOException
		// like giving an error message
	}
  }
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i;
	try {
		i = new FileInputStream(file);
		String output = "";
		int data;
		while ((data = i.read()) > 0) {
			if (data < 0x80) {
				output += (char) data;
			}
		}
		return output;
	} catch (FileNotFoundException e) {
		// handle the case when file cannot be found
		// instead of swallowing it in IOException
		// like giving an error message
	}
  }
  public void saveContent(String content) throws IOException {
    FileOutputStream o;
	try {
		o = new FileOutputStream(file);
		for (int i = 0; i < content.length(); i += 1) {
			o.write(content.charAt(i));
		}
	} catch (FileNotFoundException e) {
		// handle the case when file cannot be found
		// instead of swallowing it in IOException
		// like giving an error message
	}
  }
}
