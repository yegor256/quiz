import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
	private File file;

	public Parser(File fileToParse) {
		if (fileToParse == null) {
			throw new IllegalArgumentException("File to parse can not be null");
		}
		this.file = fileToParse;
	}

	public synchronized void setFile(File f) {
		file = f;
	}

	public synchronized File getFile() {
		return file;
	}

	public synchronized String getContent() throws IOException {
		FileInputStream readStream = null;
		try {
			readStream = new FileInputStream(file);
			StringBuffer output = new StringBuffer();
			int data;
			while ((data = readStream.read()) > 0) {
				output.append(data);
			}

			return output.toString();

		} finally {
			if (readStream != null) {
				readStream.close();
			}
		}
	}

	public synchronized String getContentWithoutUnicode() throws IOException {
		FileInputStream writeStream = null;
		try {
			writeStream = new FileInputStream(file);
			StringBuffer output = new StringBuffer();
			int data;
			while ((data = writeStream.read()) > 0) {
				if (data < 0x80) {
					output.append(data);
				}
			}
			return output.toString();
		} finally {
			if (writeStream != null) {
				writeStream.close();
			}
		}
	}

	public synchronized void saveContent(String content) throws IOException {
		FileOutputStream writeStream = null;
		BufferedOutputStream bufferedWriteStream = null;
		try {
			if (content != null && content.trim().length() > 0) {
				writeStream = new FileOutputStream(file);
				bufferedWriteStream = new BufferedOutputStream(writeStream);
				bufferedWriteStream.write(content.getBytes());
			}
		} finally {
			if (writeStream != null && bufferedWriteStream != null) {
				writeStream.close();
				bufferedWriteStream.close();
			}
		}
	}
}
