package ff.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
	private File file;
	private FileInputStream fileInputStream ;
	String output = "";
	int data;

	public synchronized void setFile(File file) {
		this.file = file;
	}
	public synchronized File getFile() {
		return file;
	}
	public String getContent(boolean withUniCode) throws IOException {
		fileInputStream = new FileInputStream(file);
		while ((data = fileInputStream.read()) > 0) {
			output += (char) data;
		}
		return output;
	}
	public String getContentWithoutUnicode() throws IOException {
		fileInputStream = new FileInputStream(file);
		while ((data = fileInputStream.read()) > 0) {
			if (data < 0x80) {
				output += (char) data;
			}
		}
		return output;
	}
	public void saveContent(String content) throws IOException {
		@SuppressWarnings("resource")
		FileOutputStream fileOutPutStream = new FileOutputStream(file);
		for (int i = 0; i < content.length(); i += 1) {
			fileOutPutStream.write(content.charAt(i));
		}
	}
}
