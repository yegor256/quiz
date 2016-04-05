import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
	
	public String getContent(File file) throws IOException {
		return getContent(file, true);
	}

	public String getContentWithoutUnicode(File file) throws IOException {
		return getContent(file, false);
	}

	public String getContent(File file, boolean includeUnicode) throws IOException {
		String output = "";
		try(
			FileInputStream i = new FileInputStream(file);
		){
			int data;
			while ((data = i.read()) > 0) {
				if(includeUnicode || data < 0xFF) {
					output += (char) data;
				}
			}			
		}
		return output;
	}
	
	public void saveContent(File file, String content) throws IOException {
		try(
			FileOutputStream o = new FileOutputStream(file);
		){
			for (int i = 0; content != null &&  i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		}
	}
	
}
