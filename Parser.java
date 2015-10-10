import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Parser {
	private File file;
	private String content = null;
	private String contentWithoutUnicode = null;

	public Parser(File f) {
		file = f;
	}

	public synchronized String getContent() throws IOException {
		if (content == null) {
			FileInputStream i = new FileInputStream(file);
			content = "";
			int data;
			while ((data = i.read()) > 0) {
				content += (char) data;
			}
			i.close();
		}
		return content;
	}

	public synchronized String getContentWithoutUnicode() throws IOException {
		if (contentWithoutUnicode == null) {
			FileInputStream i = new FileInputStream(file);
			contentWithoutUnicode = "";
			int data;
			while ((data = i.read()) > 0) {
				if (data < 0x80) {
					contentWithoutUnicode += (char) data;
				}
			}
			i.close();
		}
		return contentWithoutUnicode;
	}

	public synchronized void saveContent(String content) throws IOException {
		FileOutputStream o = new FileOutputStream(file);
		for (int i = 0; i < content.length(); i += 1) {
			o.write(content.charAt(i));
		}
		o.close();
		content = null;
		contentWithoutUnicode = null;
	}
}