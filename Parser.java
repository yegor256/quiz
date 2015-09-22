import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
	private File file;

	 

	/*
	 * set file name
	 * 
	 * @Param file set file object.
	 */
	public Parser(File file) {
		this.file = file;
	}

	/**
	 * Read content of file in byte stream than convert to string
	 * 
	 * */
	public String getContent() {
		FileInputStream fis = null;
		String output = "";
		try {
			fis = new FileInputStream(file);
			int data;
			while ((data = fis.read()) > 0) {
				output += (char) data;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return output;
	}
	/**
	 * Read content of file in byte stream than convert to string
	 * 
	 * */
	public String getContentWithoutUnicode() {
		FileInputStream fis = null;
		String output = "";
		try {
			fis = new FileInputStream(file);

			int data;
			while ((data = fis.read()) > 0) {
				if (data < 0x80) {
					output += (char) data;
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return output;
	}

	/**
	 * Write a byte of data into file If not any file than create it
	 * 
	 * */
	/**
	 * Write a byte of data into file If not any file than create it
	 * 
	 * */
	public synchronized void saveContent(String content) {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				fout.write(content.charAt(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fout != null)
					fout.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}