import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {

	private static final int UNICODE_TEST_CHAR = 0x80;

	public synchronized void saveContentToFile(final String content, final File file) throws FileNotFoundException, IOException {
		final FileOutputStream o = new FileOutputStream(file);

		for (int i = 0; i < content.length(); i++) {
			o.write(content.charAt(i));
		}		  
		o.close();
	}

	public synchronized String getContentOfFile(final File file) throws IOException {
		return getGeneralTheContentContentOfFile(file, false);
	}

	private String getGeneralTheContentContentOfFile(final File file, boolean isUnicode) throws FileNotFoundException, IOException {
		FileInputStream i = new FileInputStream(file);
		String output = "";
		int data;
		while ((data = i.read()) > 0) {
			if (isUnicode) {
				if (data < UNICODE_TEST_CHAR) {
					output += (char) data;
				}
			} else {
				output += (char) data;				
			}
		}
		i.close();
		return output;
	}

	public synchronized String getContentOfFileWithoutUnicode(final File file) throws FileNotFoundException, IOException {
		return getGeneralTheContentContentOfFile(file, true);
	}
}
