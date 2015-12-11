
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is thread safe.
 */
public class Parser {
	final private File file;

	public Parser(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public String getContent() throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
	}

	public String getContentWithoutUnicode() throws IOException {
		return getContent().replaceAll("[^\\x00-\\x7F]", "");
	}

	public void saveContent(String content) throws IOException {
		Files.write(Paths.get(file.getAbsolutePath()), content.getBytes());
	}
}
