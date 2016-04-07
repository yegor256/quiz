package test.refactor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class is thread safe.
 */
public class ParserContentImpl implements Parser {
	
	private final File file;

	public File getFile() {
		synchronized(file) {
			return file;
        }
	}

	public ParserContentImpl(File file) {
		this.file = file;
	}

	public String getContent() throws IOException {
		InputStream i = null;
		StringBuilder output = null;
		try {
			i = new FileInputStream(file);
			output = new StringBuilder();
			int data;
			while ((data = i.read()) > 0) {
				output.append((char) data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			i.close();
		}
		
		return output.toString();
	}

	public void saveContent(String content) throws IOException {
		OutputStream o = null;
		try {
			o = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			o.close();
		}
	}
}
