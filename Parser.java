import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is thread safe.
 */
public class Parser {
	private static final int FINAL_UNICODE_VALUE = 0x80;
	private File mFile;

	public synchronized void setFile(File f) {
		mFile = f;
	}

	public synchronized File getFile() {
		return mFile;
	}

	/* read() function returns -1 if there is no more data because the end of
	 the file has been reached.*/
	public synchronized String getContent() throws IOException {
            StringBuilder stringBuilder;
            try (FileInputStream fileInputStream = new FileInputStream(mFile)) {
                stringBuilder = new StringBuilder();
                int data;
                while ((data = fileInputStream.read()) != -1) {
                    stringBuilder.append(String.valueOf(data));
		}
            }
		return stringBuilder.toString();
	}

	// used a static field for filtering out unicode
	public synchronized String getContentWithoutUnicode() throws IOException {
            StringBuilder stringBuilder;
            try (FileInputStream fileInputStream = new FileInputStream(mFile)) {
                stringBuilder = new StringBuilder();
                int data;
                while ((data = fileInputStream.read()) != -1) {
                    if (data < FINAL_UNICODE_VALUE) {
                        stringBuilder.append(String.valueOf(data));
			}
		}
            }
		return stringBuilder.toString();
	}

	// Using try-with-resources statement closes any resources(file in this
	// case) itself
	// Use synchronized keyword to access the file in atomic way.
	public synchronized void saveContent(String content)
			throws FileNotFoundException {
		try (PrintWriter printWriter = new PrintWriter(mFile)) {
			printWriter.println(content);
		}
	}
}

