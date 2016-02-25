import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
	private File file;
	
	/**
	 * Function to set file.
	 */
	public synchronized void setFile(File f) {
		this.file = f;
	}
	
	/**
	 * Function to retrieve the file
	 * @return null if empty.
	 */
	public synchronized File getFile() {
		return this.file;
	}

	/**
	 * Function to get the content of the file as a String.
	 * @return file content if successful, null if empty.
	 */
	public synchronized String getContent() throws IOException {
		String output = null;
		
		if(this.file != null) {
			FileInputStream is = new FileInputStream(this.file);
			if(is != null) {
				int data;
				while ((data = is.read()) > 0) {
					output += (char) data;
				}
				is.close();
			}
		}
		return output;
	}
	
	/**
	 * Function to get the content of the file as string without unicode.
	 * @return file content as string if successful, null if empty.
	 */
	public synchronized String getContentWithoutUnicode() throws IOException {
		String output = null;

		if(this.file != null) {
			FileInputStream is = new FileInputStream(this.file);
			if(is != null) {
				int data;
				while ((data = is.read()) > 0) {
					if (data < 0x80) {
						output += (char) data;
					}
				}
				is.close();
			}
		}
		return output;
	}
	
	/**
	 * Function to write the string content to a file.
	 */
	public synchronized void saveContent(String content) throws IOException {
		if(this.file != null) {
			FileOutputStream os = new FileOutputStream(this.file);
			
			if(os != null) {
				for (int i = 0; i < content.length(); i++) {
					os.write(content.charAt(i));
				}
				os.close();
			}
		}
	}
}
