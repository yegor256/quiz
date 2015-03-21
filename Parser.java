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

	public synchronized String getContent() {
		StringBuffer output = new StringBuffer();
		FileInputStream i = null;
		try {
			i = new FileInputStream(file);

			int data;
			while ((data = i.read()) > 0) {
				// output += (char) data;
				output.append((char) data);
			}
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (i != null)
					i.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		return output.toString();
	}


	public synchronized String getContentWithoutUnicode() throws IOException {
		StringBuffer output = new StringBuffer();
		FileInputStream i = null;
		try {
			i = new FileInputStream(file);
			int data;
			while ((data = i.read()) > 0) {
				if (data < 0x80) {
					// output += (char) data;
					output.append((char) data);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (i != null)
					i.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		return output.toString();
	}

	public synchronized void saveContent(String content) throws IOException {
		FileOutputStream o = null; 
		try{
			o = new FileOutputStream(file);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			byte[] contentAsBytes = content.getBytes();
			o.write(contentAsBytes);
			o.flush();
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (o != null) {
					o.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
