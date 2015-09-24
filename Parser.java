import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

	public synchronized String getContent() throws IOException {
		FileInputStream i = null;
		String output = "";

		try {
			i = new FileInputStream(file);
			int data;
			while ((data = i.read()) > 0) {
				output += (char) data;
			}
		} finally {
			safeClose(i);
		}
		return output;
	}

	private void safeClose(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (Exception e) {
				// ignore
			}
		}
	}

	public synchronized String getContentWithoutUnicode() throws IOException {
		FileInputStream i = null;
		String output = "";
		try {
			i = new FileInputStream(file);
			int data;
			while ((data = i.read()) > 0) {
				if (data < 0x80) {
					output += (char) data;
				}
			}
		} finally {
			safeClose(i);
		}
		return output;
	}

	public synchronized void saveContent(String content) throws IOException {
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		} finally {
			safeClose(o);
		}
	}

	private void safeClose(OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (Exception e) {
				// ignore
			}
		}
	}

}
