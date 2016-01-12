import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * This class is thread safe.
 */
public class Parser
{
	private File file;

	public Parser(File file)
	{
		this.file = file;
	}

	public synchronized String getContent() throws IOException
	{
		int read;
		char[] buffer = new char[1024];
		StringBuilder content = new StringBuilder();
		Reader in = new BufferedReader(new FileReader(file));
		do {
			read = in.read(buffer);
			if (read > 0) {
				content.append(buffer, 0, read);
			}
		} while (read >= 0);
		in.close();
		return content.toString();
	}

	public synchronized String getContentWithoutUnicode() throws IOException
	{
		int read;
		StringBuilder content = new StringBuilder();
		Reader in = new BufferedReader(new FileReader(file));
		while ((read = in.read()) > 0) {
			if (read < 0x80) {
				content.append(read);
			}
		} while (read >= 0);
		in.close();
		return content.toString();
	}

	public synchronized void saveContent(String content) throws IOException
	{
		Writer out = new BufferedWriter(new FileWriter(file));
		out.write(content);
		out.close();
	}
}
