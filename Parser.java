import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	
	public String getContent() throws IOException {
		return getContent(true);
	}
	
	public String getContentWithoutUnicode() throws IOException {
		return getContent(false);
	}
	
	private String getContent(boolean hasUnicode) throws IOException {
		FileInputStream i = null;
		String output = "";
		try {
			int data;
			i = new FileInputStream(file);
			while ((data = i.read()) > 0) {
				output += (char) data;
				if (!hasUnicode || (hasUnicode && data < 0x80)) {
					output += (char) data;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			// finally close input stream to avoid resource leak
			try {
				if (i != null) {
					i.close();
				}
			} catch (IOException e) {
				// ignore
			}
		}
		return output;
	}
	public void saveContent(String content) throws IOException {
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
			// write all buffered bytes to the file 
			// and close it to avoid resource leak
			o.flush();
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// finally close output stream to avoid resource leak
			try {
				if (o != null) {
					o.close();
				}
			} catch (IOException e) {
				// ignore
			}
		}
	}
}
