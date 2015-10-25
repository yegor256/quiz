import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

	private File file;

	public Parser(File file) {
		setFile(file);
	}

	public synchronized void setFile(File file) {
		this.file = file;
	}

	public synchronized File getFile() {
		return this.file;
	}

	/**
	 * Get the content of the file as a string text
	 * 
	 * @param withUnicode
	 *            If false, we get the content of the file without unicode
	 * 
	 *            return The content of the file
	 */
	public String getContent(boolean withUnicode) throws IOException {
		FileInputStream inputStream = null;

		try (FileInputStream inputStream = new FileInputStream(file)) {
			StringBuilder output = new StringBuilder("");
			int data;
			while ((data = inputStream.read()) > 0) {
				if (withUnicode || data < 0x80) {
					output.append((char) data);
				}
			}

			return output.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save a content to the file
	 * 
	 * @param withUnicode
	 *            If false, we get the content of the file without unicode
	 * 
	 *            return The content of the file
	 */
	public void saveContent(String content) throws IOException {
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			if (!file.exists()) {
				file.createNewFile();
			}

			for (int i = 0; i < content.length(); i += 1) {
				outputStream.write(content.charAt(i));
			}

			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
