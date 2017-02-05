import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

	private File file;
	private static Parser instance = null;

	private Parser() {
	}

	public static Parser getInstance() {
		if (instance == null) {
			synchronized (Parser.class) {
				if (instance == null) {
					instance = new Parser();
				}
			}
		}
		return instance;
	}

	public synchronized void setFile(File f) {
		file = f;
	}

	public synchronized File getFile() {
		return file;
	}

	public String getContent() throws IOException {
		String output = "";
		FileInputStream i = null;
		try {
			i = new FileInputStream(file);
			int data;
			while ((data = i.read()) > 0) {
				output += (char) data;
			}
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		} finally {
			if (i != null) {
				i.close();
			}
		}
		return output;
	}

	public String getContentWithoutUnicode() throws IOException {
		String output = "";
		FileInputStream i = null;
		try {
			i = new FileInputStream(file);
			int data;
			while ((data = i.read()) > 0) {
				if (data < 0x80) {
					output += (char) data;
				}
			}
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		} finally {
			if (i != null) {
				i.close();
			}
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
			o.flush();
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		} finally {
			if (o != null) {
				o.close();
			}
		}
	}
}
