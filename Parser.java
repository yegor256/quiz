import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

	public String getContent(File file) throws IOException {
		return getContent(file, true);
	}

	public String getContentWithoutUnicode(File file) throws IOException {
		return getContent(file, false);
	}

	public void saveContent(File file, String content) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			fileOutputStream.write(content.getBytes());
		}
	}

	private String getContent(File file, boolean includeUnicode) throws IOException {
		String output = "";
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			int data = br.read();
			while (data > 0) {
				if (includeUnicode || data < 0x80) {
					output += data;
				}
			}
		}
		return output;
	}
}
