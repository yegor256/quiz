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
	private File file;

	private static final int DEFAULT_BUFFER_SIZE = 8192; // 1024 bytes
	
	private static final int UNICODE_MAX_RANGE = 0x80;
	
	public synchronized void setFile(File f) {
		file = f;
	}

	public synchronized File getFile() {
		return file;
	}

	public String getContent() throws IOException {
		return getContent(true);
	}

	public String getContentWithoutUnicode() throws IOException {
		return getContent(false);
	}
	
	private synchronized String getContent(boolean includeUnicode) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(file));
		StringBuilder strBuilder = new StringBuilder();
		byte buffer[] = new byte[DEFAULT_BUFFER_SIZE];
		
		int data;
		while ((data = in.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
			for (int i = 0; i < data; ++i) {
				if (includeUnicode || buffer[i] < UNICODE_MAX_RANGE) {
					strBuilder.append((char) buffer[i]);
				}
			}
		}
		in.close();
		return strBuilder.toString();
	}

	public synchronized void saveContent(String content) throws IOException {
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		out.write(content.getBytes());
		out.close();
	}
}
