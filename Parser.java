package sb.pk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
	private File file;

	public synchronized void setFile(File f) {
		this.file = f;
	}

	public synchronized File getFile() {
		return file;
	}

	public synchronized String getContent() throws IOException {
		String output = "";
		try (FileInputStream i = new FileInputStream(file)) {
			int data;
			while ((data = i.read()) > 0) {
				output += (char) data;
			}
		} catch (NullPointerException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return output;
	}

	public synchronized String getContentWithoutUnicode() throws IOException {
		String output = "";
		try (FileInputStream i = new FileInputStream(file)) {
			int data;
			while ((data = i.read()) > 0) {
				if (data < 0x80) {
					output += (char) data;
				}
			}
		} catch (NullPointerException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return output;
	}

	public synchronized void saveContent(String content) throws IOException {
		if (content != null) {
			try (FileOutputStream o = new FileOutputStream(file)) {
				for (int i = 0; i < content.length(); i += 1) {
					o.write(content.charAt(i));
				}
			} catch (NullPointerException e) {
				throw e;
			} catch (FileNotFoundException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			}
		}
	}
}
