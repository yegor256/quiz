import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 * 
 * I started by wrapping some unit tests on the code as it was.
 * 
 * Then I refactored into a shared method with a parameter for retrieving content.
 * 
 * I would continue by refactoring into try with resources to ensure closes, and likely using IOUtils instead.
 * 
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
		return getContent(true);
	}

	public String getContentWithoutUnicode() throws IOException {
		return getContent(false);
	}

	public String getContent(boolean includeUnicode) throws IOException {
		FileInputStream i = new FileInputStream(file);
		String output = "";
		int data;
		while ((data = i.read()) > 0) {
			if(includeUnicode || data < 0x80) {
				output += (char) data;
			}
		}
		return output;
	}
	
	public void saveContent(String content) throws IOException {
		FileOutputStream o = new FileOutputStream(file);
		for (int i = 0; i < content.length(); i += 1) {
			o.write(content.charAt(i));
		}
	}
}
