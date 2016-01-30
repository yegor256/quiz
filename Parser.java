import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
	
	private ThreadLocal<File> file = new ThreadLocal<>();

	public synchronized void setFile(File f) {
		file.set(f);
	}

	public synchronized File getFile() {
		return file.get();
	}

	public String getContent() throws IOException {
		StringBuilder output = new StringBuilder("");
		File file = getFile();
		try (BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(file))) {
			int data;
			while ((data = bis.read()) > -1) {
				output.append((char) data);
			}
		}
		return output.toString();
	}

	public String getContentWithoutUnicode() throws IOException {
		StringBuilder output = new StringBuilder("");
		File file = getFile();
		try (BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(file))) {
			int data;
			while ((data = bis.read()) > -1) {
				if (data < 0x80) {
					output.append((char) data);
				}
			}
		}
		return output.toString();
	}

	public void saveContent(String content) throws IOException {
		File file = getFile();
		try (FileOutputStream o = new FileOutputStream(file)) {
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		}
	}
}
