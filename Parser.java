import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * This class is thread safe.
 */
public class Parser {
	private File file;

	public Parser(File file) {
		this.file = file;
	}

	public synchronized File getFile() {
		return file;
	}

	public String getContent(String charset) throws IOException {
		InputStream is = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(is, charset);
		
		return this.read(isr);
	}

	public String getContentWithoutUnicode() throws IOException {
		InputStream is = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(is);
		
		return this.read(isr);
	}
	
	public String read(InputStreamReader isr) throws IOException {
		BufferedReader br = new BufferedReader(isr);
		StringBuilder output = new StringBuilder ();
		
		while (br.readLine() != null) {
			output.append(br.readLine());
		}
		
		br.close();
		
		return output.toString();
	}

	public void saveContent(String content) throws IOException {
		OutputStream os = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);
		
		for (int i = 0; i < content.length(); i += 1) {
			bw.write(content.charAt(i));
		}
		
		bw.close();
	}
}
