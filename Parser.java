import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;



/**
 * This is a File parser which provides the ability to read and write from a
 * file. This class is thread safe.
 */
public class Parser {

	// File to be read/written to
	private File file;

	/**
	 * Sets the File reference.
	 * 
	 * @param f
	 *            File reference
	 */
	public synchronized void setFile(File f) {
		file = f;
	}

	/**
	 * Accessor for the file.
	 * 
	 * @return file reference.
	 */
	public synchronized File getFile() {
		return file;
	}

	/**
	 * Method to fetch the contents from the file.
	 * 
	 * @return contents of file as string
	 * @throws IOException
	 *             thrown in case of any I/O failures
	 */
	public synchronized String getContent() throws IOException {
		return getContent(true);
	}

	/**
	 * Method to fetch the contents from the file without unicode chars. return
	 * contents of file as string
	 * 
	 * @return contents of file as string
	 * @throws IOException
	 *             thrown in case of any I/O failures
	 */
	public synchronized String getContentWithoutUnicode() throws IOException {
		return getContent(false);
	}

	/**
	 * Method to fetch the contents of file.
	 * 
	 * @param includeUniCode
	 *            if set to true includes unicode chars else doesn't.
	 * @return contents of file as string
	 * @throws IOException
	 *             thrown in case of any I/O failures
	 */
	public synchronized String getContent(boolean includeUniCode)
			throws IOException {
		if (file == null || !file.isFile()) {
			throw new IllegalArgumentException("Not a valid file - " + file);
		}
		StringBuilder output = new StringBuilder("");
		;
		BufferedInputStream bus = null;
		try {
			bus = new BufferedInputStream(new FileInputStream(file));
			int data;
			while ((data = bus.read()) > 0) {
				if (!includeUniCode) {
					if (data < 0x80) {
						output.append((char) data);
					}
				} else {
					output.append((char) data);
				}
			}
		} finally {
			bus.close();
		}
		return output.toString();
	}

	/**
	 * Method to save the given contents on to the file.
	 * 
	 * @param content
	 *            String content to be written on the file
	 * @throws IOException
	 *             thrown in case of any I/O failures
	 */
	public synchronized void saveContent(String content) throws IOException {
		if (file == null || !file.isFile()) {
			throw new IllegalArgumentException("Not a valid file - " + file);
		} else if (!file.canWrite()) {
			throw new IllegalArgumentException("File not writable - " + file);
		}
		PrintWriter pWriter = new PrintWriter(file);
		try {
			pWriter.write(content);
		} finally {
			pWriter.close();
		}
	}
}
