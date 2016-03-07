package quiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
	private File file;

	public Parser(File file) {
		this.file = file;
	}

	public final synchronized void setFile(File file) {
		this.file = file;
	}

	public final synchronized File getFile() {
		return file;
	}

	public final String getContent() throws IOException {
		StringBuilder output = new StringBuilder();
		int data;
		FileInputStream inputStream = new FileInputStream(this.file);
		while ((data = inputStream.read()) > 0) {
			output.append((char) data);
		}
		inputStream.close();
		return output.toString();
	}

	public final String getContentWithoutUnicode() throws IOException {
		FileInputStream inputStream = new FileInputStream(file);
		StringBuilder output = new StringBuilder();
		int data;
		while ((data = inputStream.read()) > 0) {
			if (data < 128) {
				output.append((char) data);
			}
		}
		inputStream.close();
		return output.toString();
	}

	public void saveContent(String content) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(file);
		for (int i = 0; i < content.length(); i += 1) {
			outputStream.write(content.charAt(i));
		}
		outputStream.close();
	}
}