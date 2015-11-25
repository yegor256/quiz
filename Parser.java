import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 * 
 * Change class name to ContentService 
 * Remove "Content" from the methods
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
		StringBuilder contentBuilder = new StringBuilder();

		try {
			inputStream = new FileInputStream(file);
			int data;
			while ((data = inputStream.read()) > 0) {
				contentBuilder.append((char) data);
			}

		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		return contentBuilder.toString();
	}

	public synchronized String getContentWithoutUnicode() throws IOException {
		FileInputStream inputStream = null;
		StringBuilder contentBuilder = new StringBuilder();

		try {
			inputStream = new FileInputStream(file);
			int data;
			while ((data = inputStream.read()) > 0) {
				if (data < 0x80) {
					contentBuilder.append((char) data);
				}
			}
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		return contentBuilder.toString();
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