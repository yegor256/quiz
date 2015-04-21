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

	// needs to be synchronized (here we can use synchronization as a block or
	// method level)
	public synchronized String getContent() throws IOException {

		// Checking file is empty
		if (file == null || !file.isFile()) {

			//we can also throw an custom exception with description
			throw new IOException("File is empty!");
		}
		FileInputStream i = new FileInputStream(file);

		String output = "";
		int data;

		//the comparison should use -1 as a lowest limit
		while ((data = i.read()) !=-1) {
			output += (char) data;
		}

		// need to be closed before returning the response
		i.close();

		return output;
	}
	
	// needs to be synchronized (here we can use synchronization as a block or
	// method level)
	public synchronized String getContentWithoutUnicode() throws IOException {
		// Checking file is empty
		if (file == null || !file.isFile()) {
			//we can also throw an custom exception with description
			throw new IOException("File is empty!");
		}

		FileInputStream i = new FileInputStream(file);

		String output = "";
		int data;
		//the comparison should use -1 as a lowest limit
		while ((data = i.read()) != -1) {
			if (data < 0x80) {
				output += (char) data;
			}
		}

		// need to be closed before returning the response
		i.close();

		return output;
	}

	// needs to be synchronized (here we can use synchronization as a block or
	// method level)
	public synchronized void saveContent(String content) throws IOException {

		// Checking file is empty or content empty
		if (file == null || !file.isFile() || content == null
				|| content.isEmpty()) {

			//we can also throw an custom exception with description
			throw new IOException("File or Content is empty!");
		}

		FileOutputStream o = new FileOutputStream(file);
		//we can skip this loop by writing the content directly
		for (int i = 0; i < content.length(); i += 1) {
			o.write(content.charAt(i));
		}

		// need to close the file output stream
		o.flush();
		o.close();
	}
}
