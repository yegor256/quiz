import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {

	/**
	 * File to parse
	 */
	private File file;
	
	/**
	 * Setter for the {@link File} to parse
	 * @param f - the File to parse
	 */
	public synchronized void setFile(File f) {
		file = f;
	}
	
	/**
	 * Getter for the {@link File} to parse
	 * @return - the File to parse
	 */
	public synchronized File getFile() {
		return file;
	}
	
	/**
	 * Gets the {@link File} contents 
	 * @return - the File contents as a {@link String}
	 * @throws IOException - thrown exception if no file was found
	 */
	public String getFileContent() throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		String output = "";
		int data;
		while ((data = fileInputStream.read()) > 0) {
			output += (char) data;
		}
		// Close the input stream to prevent a leak
		fileInputStream.close();
		return output;
	}
	
	/**
	 * Gets the {@link File} contents without unicode chars
	 * @return - the File contents as a {@link String}
	 * @throws IOException - thrown exception if no file was found
	 */
	public String getContentWithoutUnicode() throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		String output = "";
		int data;
		while ((data = fileInputStream.read()) > 0) {
			if (data < 0x80) {
				output += (char) data;
			}
		}
		// Close the input stream to prevent a leak
		fileInputStream.close();
		return output;
	}
	
	/**
	 * Saves the {@link File} contents 
	 * @param content - the String to store in the File
	 * @throws IOException - the thrown exception if no file was found
	 */
	public void saveContent(String content) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		for (int i = 0; i < content.length(); i += 1) {
			fileOutputStream.write(content.charAt(i));
		}
		
		// Close the output stream to prevent a leak
		fileOutputStream.close();
	}
}