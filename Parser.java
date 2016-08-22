
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

	private final File file;

	public Parser(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}

	public String getContent() throws IOException {
		FileInputStream i = new FileInputStream(file);
		String output = "";
		int data;
		while ((data = i.read()) > 0) {
			output += (char) data;
		}
		return output;
	}

	public String getContentWithoutUnicode() throws IOException {
		FileInputStream i = new FileInputStream(file);
		String output = "";
		int data;
		while ((data = i.read()) > 0) {
			if (data < 0x80) {
				output += (char) data;
			}
		}
		return output;
	}
	
	public void saveContent(String content) throws IOException {
		if(content == null) {
			throw new IllegalArgumentException("no content");
		}
		FileOutputStream os = new FileOutputStream(file);
		try {
			os.write(content.getBytes());
			os.flush();
		} finally {
			os.close();
		}
	}
}
