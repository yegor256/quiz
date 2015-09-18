
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {

	private final File file;

	public Parser(final File file){
		this.file = file;  
	}

	public File getFile() {
		return file;
	}

	public String getContent() throws IOException {
		String output = "";
		//TODO: Use BufferedStream
		try (FileInputStream inputStream = new FileInputStream(file)) {
			int data;
			while ((data = inputStream.read()) > 0) {
				output += (char) data;
			}
		} catch (IOException ex) {
			//TODO:Exception handling...
		}
		return output;
	}
	public String getContentWithoutUnicode() throws IOException {
		//TODO: Use BufferedStream
		String output = "";
		try(FileInputStream inputStream = new FileInputStream(file)){
			int data;
			while ((data = inputStream.read()) > 0) {
				if (data < 0x80) {
					output += (char) data;
				}
			}
		}catch(IOException ex){
			//TODO:Exception handling...
		}
		return output;
	}
	public void saveContent(final String content) throws IOException {
		//TODO: Use BufferedStream
		try(FileOutputStream outStream = new FileOutputStream(file)){
			for (int index = 0; index < content.length(); index += 1) {
				outStream.write(content.charAt(index));
			}
		}catch(IOException ex){
			//TODO:Exception handling...
		}
	}
}
