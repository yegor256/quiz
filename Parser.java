import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 * 
 * Change class name to ContentService Remove "Content" from the methods
 */
public class Parser {

	private File file;

	public synchronized void setFile(File f) {
		this.file = f;
	}

	public synchronized File getFile() {
		return this.file;
	}

	public synchronized String getContent() throws IOException {
		FileInputStream inputStream = null;
		String content = "";

		try {
			inputStream = new FileInputStream(file);
			int data;
			while ((data = inputStream.read()) > 0) {
				content += (char) data;
			}

		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		return content;
	}

	public synchronized String getContentWithoutUnicode() throws IOException {
		FileInputStream inputStream = null;
		String content = "";

		try {
			inputStream = new FileInputStream(file);
			int data;
			while ((data = inputStream.read()) > 0) {
				if (data < 0x80) {
					content += (char) data;
				}
			}
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		return content;
	}

	public synchronized void saveContent(String content) throws IOException {
		FileOutputStream output = null;
		
		try {
			output = new FileOutputStream(file);
			for (char c : content.toCharArray()) {
				output.write(c);
			}
		} finally {
			if (output != null) {
				output.flush();
				output.close();
			}
		}
	}
}