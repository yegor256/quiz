import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Parser {
	private File file;

	private static Logger log = Logger.getLogger(Parser.class.getName());

	private String FILE_NOT_FOUND = "File Not Found";
	private String CONTENT_CORRUPTED = "Input validation fail";

	private static final Pattern NON_ASCII = Pattern.compile("[^\\x00-\\x7F]");

	public String getContent() throws IOException {
		if (validateFile()) {
			File f = getFile();
			return new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
		}
		return null;
	}

	public String getContentWithoutUnicode() throws IOException {
		String content = getContent();
		if (content != null) {
			return NON_ASCII.matcher(content).replaceAll("");
		}
		return null;
	}

	public void saveContent(String content) throws Exception {

		if (content == null || content.isEmpty()) {
			log.log(Level.SEVERE, this.CONTENT_CORRUPTED, new IllegalArgumentException());
		}

		if (validateFile()) {
			File f = getFile();
			Files.write(f.toPath(), content.getBytes(), StandardOpenOption.APPEND);
		}
	}

	private boolean validateFile() {
		File f = getFile();
		if (f == null || !f.exists() || !f.isFile()) {
			log.log(Level.SEVERE, this.FILE_NOT_FOUND, new FileNotFoundException());
		}
		return true;
	}

	public void setFile(File newFile) {
		if (newFile == null || !newFile.exists() || !newFile.isFile()) {
			log.log(Level.SEVERE, this.FILE_NOT_FOUND, new FileNotFoundException());
		}

		synchronized (file) {
			file = newFile;
		}
	}

	public synchronized File getFile() {
		synchronized (file) {
			return file;
		}
	}
}
