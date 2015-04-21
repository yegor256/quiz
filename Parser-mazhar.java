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
		if (file == null) {
			return null;
		}
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			String output = "";
			int data;
			while ((data = fileInputStream.read()) > 0) {
				output += (char) data;
			}
			return output;
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}

	public String getContentWithoutUnicode() throws IOException {
		if (file == null) {
			return null;
		}
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			String output = "";
			int data;
			while ((data = fileInputStream.read()) > 0) {
				if (data < 0x80) {
					output += (char) data;
				}
			}
			return output;
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}

	public void saveContent(String content) throws IOException {
		if (file == null) {
			return;
		}
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				fileOutputStream.write(content.charAt(i));
			}
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		}
	}
}
