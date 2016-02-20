import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * This class is thread safe.
 */
public class Parser {
	private File file;

	public Parser(File f) {
		this.file = f;
	}

	public synchronized void setFile(File f) {
		file = f;
	}

	public synchronized File getFile() {
		return file;
	}

	public String getContent() throws IOException {
		InputStream fileInputStream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(fileInputStream,
				Charset.forName("UTF-8"));

		String content = readContent(reader);

		reader.close();
		fileInputStream.close();
		return content;
	}

	public String getContentWithoutUnicode() throws IOException {
		String content = readContent(new FileReader(file));
		return content;
	}

	private synchronized String readContent(InputStreamReader reader)
			throws IOException {
		BufferedReader br = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String content = sb.toString();
		br.close();
		return content;
	}

	public synchronized void saveContent(String content) throws IOException {
		PrintWriter pw = new PrintWriter(file);
		pw.print(content);
		pw.close();
	}
}
