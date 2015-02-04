import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

	public static Object lockObject = new Object();

	private File file;

	public synchronized void setFile(File f) {
		synchronized (lockObject) {
			file = f;
		}
	}

	public synchronized File getFile() {
		synchronized (lockObject) {
			return file;
		}
	}

	public String getContent() throws IOException {
		synchronized (lockObject) {
			return syncGetContent();
		}
	}

	public String getContentWithoutUnicode() throws IOException {
		synchronized (lockObject) {
			return syncGetContentWithoutUnicode();
		}
	}

	public void saveContent(String content) throws IOException {
		synchronized (lockObject) {
			syncSaveContent( content );
		}
	}

	private String syncGetContent() throws FileNotFoundException, IOException {
		FileInputStream i = new FileInputStream( file );
		String output = "";
		int data;
		while ((data = i.read()) > 0)
			output += (char) data;

		i.close();

		return output;
	}

	private String syncGetContentWithoutUnicode() throws FileNotFoundException, IOException {
		FileInputStream i = new FileInputStream( file );
		String output = "";
		int data;
		while ((data = i.read()) > 0) {
			if (isNotSpecialCharacter( data ))
				output += (char) data;
		}
		i.close();
		return output;
	}

	private boolean isNotSpecialCharacter(int data) {
		return data < 128;
	}

	private void syncSaveContent(String content) throws FileNotFoundException, IOException {
		FileOutputStream o = new FileOutputStream( file );
		for (int i = 0; i < content.length(); i += 1)
			o.write( content.charAt( i ) );

		o.flush();
		o.close();
	}

}
