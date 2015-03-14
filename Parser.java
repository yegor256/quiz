import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {

	private File file;

	public synchronized void setFile(File f) {
		file = f;
	}

	public synchronized File getFile() {
		return file;
	}
	/**
	 * An amelioration is to close the FileInputStream.
	 * StringBuffer is used when fetched data from the file..
	 * @return
	 * @throws IOException
	 */
	public String getContent() throws IOException {
		FileInputStream i = new FileInputStream(file);
		StringBuffer output = new StringBuffer();
		int data;
		while ((data = i.read()) > 0) {
			output.append((char) data);
		}
		i.close();
		return output.toString();
	}
	/**
	 * BufferedReader reads data in an efficient way using a customizable buffer.
	 * StringBuffer is used to concatenate the fetched String.
	 * @return
	 * @throws IOException
	 */
	public String getContent2() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuffer output = new StringBuffer();
		String data;
		while ((data = br.readLine()) != null) {
			output.append(data);
		}
		br.close();
		return output.toString();
	}
	/**
	 * Closing FileInputSteam was missing.
	 * I chose StringBuffer to concatenate the fetched character for performance reasons.
	 * @return
	 * @throws IOException
	 */
	public String getContentWithoutUnicode() throws IOException {
		FileInputStream i = new FileInputStream(file);
		StringBuffer output = new StringBuffer();
		int data;
		while ((data = i.read()) > 0) {
			if (data < 0x80) {
				output.append((char) data);
			}
		}
		i.close();
		return output.toString();
	}
	/**
	 * Closing the FileOutStream was missing.
	 * @param content
	 * @throws IOException
	 */
	public void saveContent(String content) throws IOException {
		FileOutputStream o = new FileOutputStream(file);
		for (int i = 0; i < content.length(); i += 1) {
			o.write(content.charAt(i));
		}
		o.close();
	}
	/**
	 * An amelioration of saveContent, where BufferedWriter to write a whole string
	 * instead of writing in the file character by character. 
	 * @param content
	 * @throws IOException
	 */
	public void saveContent2(String content) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(content);
		bw.close();
	}

}
