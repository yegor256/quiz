import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedReader;

/**
 * Parser's read and write operations are thread safe, however, 
 * different instances of the Parser that are accessing the 
 * same file will still have to be externally synchronized.
 */
public class Parser {
 	
 	
	private final File file;
	
	
	public Parser(File file) {
		this.file = file;
	}
	
	
	public File getFile() {
		return file;
	} 

    /**
	 * Parses the contents of the file. 
	 * 
	 * @param withUnicode if true unicode character will be included, otherwise only ascii
	 *        characters are returned
	 *        
	 * @return parsed file contents
	 * 
	 * @throws IOException if the file could not be read
	 */
	public synchronized String getContent(boolean withUnicode) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder  sb = new StringBuilder();
		
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String content = sb.toString();
		br.close();
		
		return withUnicode ? content.replaceAll("[^\\x00-\\x7f]", "") : content;
	}
	
	/**
	 * Saves the content to the file
	 * 
	 * @param content that is to be saved
	 * 
	 * @throws IOException if the contents could not be saved to the file
	 */
	public synchronized void saveContent(String content) throws IOException {
		PrintWriter pw = new PrintWriter(file);
		pw.print(content);
		pw.close();
	}

}
