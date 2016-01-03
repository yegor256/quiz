import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * This class is thread safe.
 */
public class Parser {

	public String getContent(File file) throws IOException {
		return getFileContent(file, true);
	}
	
	public String getContentWithoutUnicode(File file) throws IOException {
		return getFileContent(file, false);
	}

	public void saveContent(File file, String content) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(file);
		for (int i = 0; i < content.length(); i++) {
			outputStream.write(content.charAt(i));
		}
		outputStream.close();
	}
	
	private boolean isUnicode(int data) {
		return data >= 0x80;
	}
	
	private String getFileContent(File file, boolean includeUnicode) throws IOException {
		FileInputStream inputStream = new FileInputStream(file);
		StringBuilder output = new StringBuilder();
		int data;
		while ((data = inputStream.read()) > 0) {
			if (!isUnicode(data) || includeUnicode) {
				output.append((char) data);
			}
		}
		inputStream.close();
		return output.toString();
	}
	
}
