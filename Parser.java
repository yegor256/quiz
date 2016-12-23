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
		FileInputStream i = null;
		try {
			i = new FileInputStream(file);
			String output = "";
			int data;
			while ((data = i.read()) > 0) {
				output += (char) data;
			}
			return output;
		} finally {
			if (i != null)
				i.close();
		}
	}

	public String getContentWithoutUnicode() throws IOException {
		FileInputStream i = null;
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
		} finally {
			if (i != null)
				i.close();
		}
	}

	public void saveContent(String content) throws IOException {
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		} finally {
			if (o != null)
				o.close();
		}
	}
}
