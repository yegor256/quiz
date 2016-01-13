import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class is thread safe.
 */
public class Parser {
	private static final int BUFFER_SIZE_BYTES = 1024;
	private File file;

	public synchronized void setFile(File f) {
		file = f;
	}

	public synchronized File getFile() {
		return file;
	}

	public synchronized String getContent() throws IOException {
		return getContent(null);
	}

	public synchronized String getContentWithoutUnicode() throws IOException {
		return getContent(0x80);
	}

	private synchronized String getContent(Integer skipChar) throws IOException {
		try (InputStream i = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE_BYTES)) {
			StringBuilder output = new StringBuilder();
			int data;
			while ((data = i.read()) > 0) {
				if (skipChar == null || skipChar >= 0x80) {
					output.append((char) data);
				}
			}
			return output.toString();
		}
	}

	public synchronized void saveContent(String content) throws IOException {
		try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE_BYTES)) {
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		}
	}
}
