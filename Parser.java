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
		return file;
	}

	public String getContent() throws IOException {
		String output = "";
		int data;
		try (FileInputStream inputStream = new FileInputStream(file)) {
			while ((data = inputStream.read()) != -1) {
				output += (char) data;
			}
		}
		return output;
	}

	public String getContentWithoutUnicode() throws IOException {
		String output = "";
		int data;
		try (FileInputStream inputStream = new FileInputStream(file)) {
			while ((data = inputStream.read()) != -1) {
				if (data < 0x80) {
					output += (char) data;
				}
			}
		}
		return output;
	}

	public void saveContent(String content) throws IOException {
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			for (int i = 0; i < content.length(); i += 1) {
				outputStream.write(content.charAt(i));
			}
		}
	}
}
