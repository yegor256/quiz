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

	public File getFile() {
		return file;
	}

	public String getContent() throws IOException {
		return getContent(true);
	}

	public String getContentWithoutUnicode() throws IOException {
		return getContent(false);
	}

	private String getContent(boolean includeUnicode) throws IOException {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(getFile());
			StringBuilder output = new StringBuilder();
			int data;
			while ((data = inputStream.read()) > 0) {
				if (!isUnicode(data) || includeUnicode) {
					char c = (char) data;
					output.append(c);
				}
			}
			return output.toString();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	private boolean isUnicode(int data) {
		return data < 0x80;
	}

	public synchronized void saveContent(String content) throws IOException {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(getFile());
			for (int i = 0; i < content.length(); i += 1) {
				outputStream.write(content.charAt(i));
			}
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
	
}
