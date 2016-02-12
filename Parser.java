import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * File parser. Allows to append content to file and read content. This class is mutable and not thread safe.
 */
public class Parser {

	private File file;

	/**
	 * Get file content in a string format.
	 * @return String content of the file.
	 * @throws java.io.IOException
	 */
	public String getContent() throws IOException {
		try (FileInputStream inputStream = new FileInputStream(file)) {
			StringBuilder output = new StringBuilder();
			int data;
			while ((data = inputStream.read()) > 0) {
				output.append((char) data);
			}
			return output.toString();
		}
	}

	/**
	 * Get file content without unicode in a string format.
	 * @return String content of the file.
	 * @throws java.io.IOException
	 */
	public String getContentWithoutUnicode() throws IOException {
		try (FileInputStream inputStream = new FileInputStream(file)) {
			StringBuilder output = new StringBuilder();
			int data;
			while ((data = inputStream.read()) > 0) {
				if (data < 0x80) {
					output.append((char) data);
				}
			}
			return output.toString();
		}
	}

	/**
	 * Write content to the end of the file.
	 * @param content content to wright
	 * @throws java.io.IOException
	 */
	public void saveContent(String content) throws IOException {
		try (FileOutputStream o = new FileOutputStream(file)) {
			o.write(content.getBytes());
		}
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}
}
