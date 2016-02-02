import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * This class offers convenience methods for reading and writing String data from and to files in the file system.
 * 
 * This class is thread safe; modification of the file to use while a read or write operation is in progress does not affect this
 * read or write operation: It will complete on the previously set file.
 */
public class Parser {

	/**
	 * The buffer size to use when reading from and writing to files. Higher values mean more memory consumption but less read or
	 * write operations.
	 */
	private static final int BUFSIZE = 1024;

	/**
	 * The file to operate on.
	 */
  private File file;

	/**
	 * Sets the file to use for read and write operations.
	 * 
	 * @param f File to use for read and write operations. Must not be <code>null</code>.
	 */
  public synchronized void setFile(File f) {
		if (f == null) {
			throw new IllegalArgumentException("file must not be null");
		}
    file = f;
  }

	/**
	 * Returns the file which is used for read and write operations.
	 * 
	 * @return The file which is used for read and write operations, or <code>null</code> if it has not been set yet.
	 */
  public synchronized File getFile() {
    return file;
  }

	/**
	 * Returns the contents of the file as a string, assuming an ISO-8859-1 encoding of the file.
	 * 
	 * @return The contents of the file as a (possibly empty) string, never <code>null</code>.
	 * 
	 * @throws IOException If reading from the file fails.
	 */
  public String getContent() throws IOException {
		FileInputStream i = new FileInputStream(getFile());

		try {
			// Previous algorithm used (implicitly) ISO-8859-1 encoding,
			// as it read byte by byte and converted each into a UNICODE
			// value. So we do the same here.
			InputStreamReader reader = new InputStreamReader(i, "ISO-8859-1");
			StringBuilder output = new StringBuilder();

			char[] buf = new char[BUFSIZE];
			int read;
			// this is slightly different than previous implementation, as
			// previous implementation stopped at a NULL byte.
			while ((read = reader.read(buf)) > 0) {
				output.append(buf, 0, read);
			}

			return output.toString();
		}
		finally {
			try {
				i.close();
			}
			catch (IOException e) {
				// ignore to not generate wrong stack trace
			}
		}
  }

	/**
	 * Reads the current file's contents into a string, reading only ASCII characters (byte value lower than 0x80), ignoring other
	 * characters. The file is assumed to have ISO-8859-1 encoding.
	 * 
	 * @return A file's contents, interpreted as a string, without non-ASCII characters.
	 * @throws IOException If reading from the file fails.
	 */
  public String getContentWithoutUnicode() throws IOException {
		FileInputStream i = new FileInputStream(getFile());

		try {
			StringBuilder output = new StringBuilder();
			int data;
			while ((data = i.read()) >= 0) {
				if (data < 0x80) {
					output.append((char)data);
				}
			}
			return output.toString();
		}
		finally {
			try {
				i.close();
			}
			catch (IOException e) {
				// ignore to not generate wrong stack trace
			}
    }
  }

	/**
	 * Saves the contents of the given string to the current file. The contents are stored with an ISO-8859-1 encoding. Characters
	 * which cannot be mapped to ISO-8859-1 will be mapped to the charset's default replacement.
	 * 
	 * @param content String content to be stored to the current file.
	 * @throws IOException If writing to the file fails.
	 */
  public void saveContent(String content) throws IOException {
		FileOutputStream o = new FileOutputStream(getFile());

		try {
			ByteBuffer bytes = Charset.forName("ISO-8859-1").encode(content);
			bytes.position(0);
			byte[] buf = new byte[BUFSIZE];
			while (bytes.remaining() > 0) {
				int read = Math.min(BUFSIZE, bytes.remaining());
				bytes.get(buf, 0, read);
				o.write(buf, 0, read);
			}
		}
		finally {
			try {
				o.close();
			}
			catch (IOException e) {
				// ignore to not generate wrong stack trace
			}
		}
  }
}
