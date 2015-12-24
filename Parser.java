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

	private String getContent(boolean isUnicode) throws IOException {
		FileInputStream i = null;
		String output = "";
		if (file != null) {
			try {
				i = new FileInputStream(file);
				output = "";
				int data;
				while ((data = i.read()) > 0) {
					if (!isUnicode) {
						if (data < 0x80) {
							output += (char) data;
						}
					} else {
						output += (char) data;
					}
				}
			} finally {
				if (i != null) {
					i.close();
				}
			}
		}
		return output;
	}

	public String getContentWithoutUnicode() throws IOException {
		return getContent(false);
	}

	public synchronized void saveContent(String content) throws IOException {
		FileOutputStream o = null;
		if (file != null) {
			try {
				o = new FileOutputStream(file);
				for (int i = 0; i < content.length(); i += 1) {
					o.write(content.charAt(i));
				}
			} finally {
				if (o != null) {
					o.close();
				}
			}
		}
	}
}
