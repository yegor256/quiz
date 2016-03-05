import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ParserGenerics<T extends File> {
	private static final int UNICODE_TEST_CHAR = 0x80;

	private T t;

	public void set(T t) { this.t = t; }
	public T get() { return t; }


	public  void saveContentToFile(final String content) throws FileNotFoundException, IOException {
		final FileOutputStream o = new FileOutputStream(t);

		for (int i = 0; i < content.length(); i++) {
			o.write(content.charAt(i));
		}		  
		o.close();
	}

	public  String getContentOfFile() throws IOException {
		return getGeneralTheContentContentOfFile(t, false);
	}

	private String getGeneralTheContentContentOfFile(final T t, boolean isUnicode) throws FileNotFoundException, IOException {
		FileInputStream i = new FileInputStream(t);
		String output = "";
		int data;
		while ((data = i.read()) > 0) {
			if (isUnicode) {
				if (data < UNICODE_TEST_CHAR) {
					output += (char) data;
				}
			} else {
				output += (char) data;				
			}
		}
		i.close();
		return output;
	}

	public String getContentOfFileWithoutUnicode() throws FileNotFoundException, IOException {
		return getGeneralTheContentContentOfFile(t, true);
	}
}
