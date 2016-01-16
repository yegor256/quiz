import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

	/** The file. */
	private File file;

	/**
	 * Sets the file.
	 *
	 * @param f
	 *            the new file
	 */
	public synchronized void setFile(File f) {
		file = f;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public synchronized File getFile() {
		return file;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public synchronized String getContent() throws IOException {
		FileInputStream i = null;
		try {
			i = new FileInputStream(file);
			String output = "";
			int data;
			while ((data = i.read()) > 0) {
				output += (char) data;
			}
			return output;
		} finally {
			i.close();
		}
	}

	/**
	 * Gets the content without unicode.
	 *
	 * @return the content without unicode
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public synchronized String getContentWithoutUnicode() throws IOException {
		FileInputStream i = null;
		try {
			i = new FileInputStream(file);
			String output = "";
			int data;
			while ((data = i.read()) > 0) {
				if (data < 0x80) {
					output += (char) data;
				}
			}
			return output;
		} finally {
			i.close();
		}
	}

	/**
	 * Save content.
	 *
	 * @param content
	 *            the content
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public synchronized void saveContent(String content) throws IOException {
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		} finally {
			o.flush();
			o.close();
		}
	}
}
