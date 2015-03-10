import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
	/**
	 *  Create one instance in each JVM
	 */
	private static Parser instance = new Parser();
	
	/**
	 * Make the constructor as private
	 */
	private Parser () {
	}
	
	/**
	 * Get instance to call the others methods
	 * 
	 * @return Parser instance
	 */
	public static Parser getInstance() {
		return instance;
	}
	
	/** 
	 * Get the content from the passed File
	 * 
	 * @param file	File that needed to scan
	 * @return		The file content
	 * 
	 * @throws IOException
	 */
	public String getContent(File file) throws IOException {
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
			StringBuilder output = new StringBuilder();
		    int data;
		    
		    while ((data = bufferedInputStream.read()) > 0) {
		      output.append((char) data);
		    }
		    
		    return output.toString();
		}
	}
	
	/** 
	 * Get the content without unicode characters from the passed File
	 * 
	 * @param file	File that needed to scan
	 * @return		The file content
	 * 
	 * @throws IOException
	 */
	public String getContentWithoutUnicode(File file) throws IOException {
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
			StringBuilder output = new StringBuilder();
		    int data;
		    
		    while ((data = bufferedInputStream.read()) > 0) {
		      if (data < 0x80) {
		        output.append((char) data);
		      }
		    }
		    
		    return output.toString();
		}
	}
	
	/**
	 * Save the passed content to passed file
	 * 
	 * @param content	The content
	 * @param file		The target file
	 * 
	 * @throws IOException
	 */
	public void saveContent(String content, File file) throws IOException {
		try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			for (int i = 0; i < content.length(); i += 1) {
				bufferedOutputStream.write(content.charAt(i));
			}
		    
			bufferedOutputStream.flush();
	    }
	}
}
