import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

	/** The file attribute */
	private File file;


	/**
	 * getContent method.
	 * @return String content
	 */
	public synchronized String getContent(boolean WithoutUnicode) {
		StringBuilder output = new StringBuilder("");
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			int data;
			while ((data = fileInputStream.read()) > 0) {
				// if WithoutUnicode
				if (WithoutUnicode && (data < 0x80)) {
					output.append((char) data);
				} else {
					output.append((char) data);
				}
			}
			// Close fileInputStream
			fileInputStream.close();
		// catch exceptions
		} catch (FileNotFoundException fnfException) {
			System.err.println("File not fount : " + fnfException.getMessage());
		} catch (IOException ioException) {
			System.err.println("caught exception : " + ioException.getMessage());
		}
		return output.toString();
	}

	/**
	 * saveContent method.
	 */
	public synchronized void saveContent(String content){
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				fileOutputStream.write(content.charAt(i));
			}
			// Close fileOutputStream
			fileOutputStream.close();
			// catch exceptions
		} catch (FileNotFoundException fnfException) {
			System.err.println("File not fount : " + fnfException.getMessage());
		} catch (IOException ioException) {
			System.err.println("caught exception : " + ioException.getMessage());
		}
	}
	
	/**
	 * Setter of file attribute.
	 * @param f File
	 */
	public synchronized void setFile(File f) {
		this.file = f;
	}

	/**
	 * Getter of file attribute.
	 * @return file
	 */
	public synchronized File getFile() {
		return this.file;
	}
}
