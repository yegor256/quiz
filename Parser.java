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

	// XXX: Combine getContent... methods
	// XXX: Obtain file by getFile method
	// XXX: close method
	public synchronized String getContent(boolean unicode) throws IOException {
		FileInputStream i = new FileInputStream(getFile());
		String output = "";
		int data;
		while ((data = i.read()) > 0) {
			if (!unicode) {
				if (data < 0x80) {
					output += (char) data;
				}
			} else {
				output += (char) data;
			}
		}
		i.close();
		return output;
	}

	// XXX: synchronized saveContent method
	// XXX: Obtain file by getFile method
	// XXX: close method
	public synchronized void saveContent(String content) throws IOException {
		FileOutputStream o = new FileOutputStream(getFile());
		for (int i = 0; i < content.length(); i += 1) {
			o.write(content.charAt(i));
		}
		o.close();
	}

}
