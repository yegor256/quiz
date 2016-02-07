import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Parser {

	public String getContent(File file) throws IOException {
		return getContent(file, true);
	}

	public String getContentWithoutUnicode(File file) throws IOException {
		return getContent(file, false);
	}

	public void saveContent(File file, String content) throws IOException {
		FileOutputStream o = new FileOutputStream(file);
		for (int i = 0; i < content.length(); i += 1) {
			o.write(content.charAt(i));
		}
	}

	private String getContent(File file, boolean includeUnicode) throws IOException {
		FileInputStream i = new FileInputStream(file);
		String output = "";
		int data;
		while ((data = i.read()) > 0) {
			if (includeUnicode || data < 0x80) {
				output += (char) data;
			}
		}
		return output;
	}
}
