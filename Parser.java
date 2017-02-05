import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

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

	public synchronized String getContent() throws IOException {
		StringBuilder output = new StringBuilder();
		int data;
		try (FileInputStream input = new FileInputStream(file)) {
			while ((data = input.read()) > 0) {
				output.append((char) data);
			}
		}
		return output.toString();
	}

	public synchronized String getContentWithoutUnicode() throws IOException {
		StringBuilder output = new StringBuilder();
		try (BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)))) {
			int data;
			while ((data = input.read()) > 0) {
				if (data < 0x80) {
					output.append((char) data);
				}
			}
		}
		return output.toString();
	}

	public synchronized void saveContent(String content) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (int i = 0; i < content.length(); i += 1) {
				writer.write(content.charAt(i));
			}
		}

	}

}
