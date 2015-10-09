import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class ParserNew {

	private final String WITHOUT_UNICODE = "WTU";

	private final String WITH_UNICODE = "WU";

	private File file;

	public synchronized void setFile(final File f) {
		file = f;
	}

	public File getFile() throws FileNotFoundException {
		if (file == null) {
			throw new FileNotFoundException("File not found");
		}
		return file;
	}

	public String getContent() throws IOException {
		return getContent(WITH_UNICODE);
	}

	public String getContentWithoutUnicode() throws IOException {
		return getContent(WITHOUT_UNICODE);
	}

	public void saveContent(String content) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(getFile());
		for (int i = 0; i < content.length(); i += 1) {
			outputStream.write(content.charAt(i));
		}
		outputStream.close();
	}

	private String getContent(final String type) throws IOException {
		FileInputStream inputStream = new FileInputStream(getFile());
		String output = "";
		int data;
		while ((data = inputStream.read()) > 0) {
			if (WITHOUT_UNICODE.equalsIgnoreCase(type)) {
				if (data < 0x80) {
					output += (char) data;
				}
			} else if (WITH_UNICODE.equalsIgnoreCase(type)) {
				output += (char) data;
			}
		}
		inputStream.close();
		return output;
	}

}
