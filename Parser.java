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
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);

			int data;
			while ((data = inputStream.read()) > 0) {
				output += (char) data;
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioe) {
					throw ioe;
				}
			}
		}
		return output;
	}

	public String getContentWithoutUnicode() throws IOException {
		String output = "";
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);

			int data;
			while ((data = inputStream.read()) > 0) {
				if (data < 0x80) {
					output += (char) data;
				}
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioe) {
					throw ioe;
				}
			}
		}
		return output;

	}

	public void saveContent(String content) throws IOException {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				outputStream.write(content.charAt(i));
			}

		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ioe) {
					throw ioe;
				}
			}
		}
	}
}
