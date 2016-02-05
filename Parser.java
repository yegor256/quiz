import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * This class is thread safe.
 * @author Luiz Henrique Grossl
 */
public class Parser {
	private Path file;

	/**
	 * 
	 * @param f
	 */
	public synchronized void setFile(File f) {
		this.file = Paths.get(f.getAbsolutePath());
	}

	/**
	 * 
	 * @return
	 */
	public synchronized File getFile() {
		return this.file.toFile();
	}

	/**
	 * get parsed content in unicode
	 * 
	 * @return content
	 * @throws IOException
	 */
	public synchronized String getContent() throws IOException {
		BufferedReader reader = Files.newBufferedReader(this.file, Charset
				.defaultCharset());

		StringBuffer contentBuffer = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			contentBuffer.append(line);
		}

		return contentBuffer.toString();
	}

	/**
	 * get parsed content without unicode
	 * 
	 * @return content
	 * @throws IOException
	 */
	public synchronized String getContentWithoutUnicode() throws IOException {
		BufferedReader reader = Files.newBufferedReader(this.file, Charset
				.defaultCharset());

		StringBuffer contentBuffer = new StringBuffer();
		int data;
		while ((data = reader.read()) > 0) {
			if (data < 0x80) {
				contentBuffer.append((char) data);
			}
		}

		return contentBuffer.toString();
	}

	/**
	 * save file content for parsing it's content
	 * 
	 * @param content
	 * @throws IOException
	 */
	public synchronized void saveContent(String content) throws IOException {
		Files.write(this.file,
				content.getBytes(), StandardOpenOption.CREATE);
	}
}
