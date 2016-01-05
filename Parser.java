import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe because it does not have fields with mutable states.
 */
public final class Parser {

	private Parser() {
	}

	/**
	 * Method returns string content from file.
	 *
	 * @param file
	 *            File
	 * @throws IOException
	 *             the IOException
	 */
	public static String getContent(File file) throws IOException {
		int data;
		String output = "";
		FileInputStream i = null;
		try {
			i = new FileInputStream(file);
			while ((data = i.read()) > 0) {
				output += (char) data;
			}
		} finally {
			if (i != null) {
				i.close();
			}
		}
		return output;
	}

	/**
	 * Method returns string content from file without unicode.
	 *
	 * @param file
	 *            File
	 * @throws IOException
	 *             the IOException
	 */
	public static String getContentWithoutUnicode(File file) throws IOException {
		int data;
		String output = "";
		FileInputStream i = null;
		try {
			i = new FileInputStream(file);
			while ((data = i.read()) > 0) {
				if (data < 0x80) {
					output += (char) data;
				}
			}
		} finally {
			if (i != null) {
				i.close();
			}
		}
		return output;
	}

	/**
	 * Method saves string content into the file.
	 *
	 * @param file
	 *            File
	 * @param content
	 *            String
	 * @throws IOException
	 *             the IOException
	 */
	public static void saveContent(File file, String content) throws IOException {
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		} finally {
			if (o != null) {
				o.close();
			}
		}
	}
}