import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
	private File file;

	public Parser(File f) {
		file = f;
	}

	public File getFile() {
		return file;
	}

	public String getContent() throws IOException {
		StringBuilder builder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			builder.append(br.readLine());
		}
		return builder.toString();
	}

	public String getContentWithoutUnicode() throws IOException {
		StringBuilder builder = new StringBuilder();
		try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file))) {
			byte[] bytes = new byte[1024];
			while (fis.read(bytes) > 0) {
				for (int i = 0; i < bytes.length; i++) {
					if (bytes[i] < 0x80) {
						builder.append((char) bytes[i]);
					}
				}
			}
		}
	    return builder.toString();
	}

	public void saveContent(String content) throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.write(content);			
		}
	}
}
