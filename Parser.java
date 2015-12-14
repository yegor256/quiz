import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * This class is thread safe.
 */

/* 
 * General changes.
 * Indentation update  
 * Handling the system errors thru a common Exception interface 
*/
// maybe we could use another strategy to manage concurrency. such as Atomic Variables. 
// and if we only have 1 file by application maybe handlign the helper as a singletoon 

public class Parser {
	public static void main(String[] args){
		// class testing.. 
		try{
			Parser parser = new Parser("test.txt");
			System.out.println(parser.getContent());
			System.out.println(parser.getContentWithoutUnicode());
			//initial status of the file "this is the first content of the file.... "
			parser.saveContent("This is the new content of this file");
			System.out.println(parser.getContent());
			URL url = Parser.class.getResource("test.txt"); // this should be a different output to validate is reading other file 
			File file = new File(url.getPath());
			System.out.println(parser.getContentWithoutUnicode(file));
			//pending test Threads, this could be tested with a ExecutorService. 
		}catch(ParserException pe){
			System.out.println(pe.getMessage());
		}catch(Exception e){
			System.out.println("---->" + e.getMessage());
		}
		
	}
	
	private File file;
	// Create specific constructors to allow the API users create the object with the required information. 
	// assigning the file this way should not be synchronized because only the thread creating the instance has access to the constructor. 
	public Parser(){}
	public Parser(File f) throws ParserException{
		super();
		validateFile(f);
		this.file = f;
	}
	public Parser(String filePath) throws ParserException{
		super();
		try{
			URL url = getClass().getResource(filePath);
			this.file = new File(url.getPath());
			validateFile();
		}catch(Exception e){
			throw new ParserException("File "+filePath+" not found!");
		}
	}
	
	public void setFile(File f) throws ParserException{
		validateFile(f);
		synchronized(this.file) {
			file = f;
		}
	}
	
	public File getFile() {
		synchronized(this.file) {
			return file;
		}
	}

	public FileInputStream getISFromFile(File f) throws ParserException {
		// create this method to be able to handle errors or concurrency.
		validateFile(f);
		FileInputStream i = null;
		try{
			i = new FileInputStream(f);
		}catch(Exception e){
			throw new ParserException(e);
		}
		return i;
	}
	
	public String getContent() throws ParserException {
		synchronized(this.file) {
			return (getContent(this.file));
		}
	}
	
	public String getContentWithoutUnicode() throws ParserException {
		synchronized(this.file) {
			return getContentWithoutUnicode(this.file);
		}
	}
	
	// allow the user get file content regardless is the instanciated or not. 
	// if this is the expected behavior we could make this method private
	public String getContent(File file) throws ParserException {
		String output = "";
		try{
			FileInputStream i = getISFromFile(file);
			int data;
			while ((data = i.read()) > 0) {
				output += (char) data;
			}
			i.close();
			i = null;
		}catch(IOException e){
			throw new ParserException(e);
		}
		return output;
	}
	public String getContentWithoutUnicode(File file) throws ParserException {
		String output = "";
		try{
			FileInputStream i = getISFromFile(file);
			int data;
			while ((data = i.read()) > 0) {
				if (data < 0x80) {
					output += (char) data;
				}
			}
			i.close();
			i = null;
		}catch(IOException e){
			throw new ParserException(e);
		}
		return output;
	}

	public synchronized void saveContent(String content) throws ParserException {
		validateFile();
		synchronized(this.file) {
			FileOutputStream o = null;
			try{
				o = new FileOutputStream(file);
				for (int i = 0; i < content.length(); i += 1) {
					o.write(content.charAt(i));
				}
				o.close();
				o = null;
			}catch(IOException e){
				throw new ParserException(e);
			}
		}
	}
	
	public void validateFile() throws ParserException{
		if (this.file == null){throw new ParserException("File not found! \nplease assign a file calling the method setFile(File file)");}
		if (!this.file.exists()){ throw new ParserException("File not found!"); }
		return;
	}
	public void validateFile(File f) throws ParserException{
		if (f == null){throw new ParserException("File not found!");}
		if (!f.exists()){ throw new ParserException("File not found!"); }
		return;
	}
}

/**
 * @author cesagonz
 * Just a Exception helper... we can enhance the error reporting and functionality with this class.
 *
 */
class ParserException extends Exception {
	private static final long serialVersionUID = 1L;
	public static final int GENERAL_ERROR = 0;
	private int code = GENERAL_ERROR;
	public ParserException() {
        super();
    }
    public ParserException(Throwable cause) {
        super(cause);
    }
    public ParserException(String message) {
        super(message);
    }
    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
    public ParserException(int code, String message) {
		super(message);
		this.code = code;
	}
    public ParserException(int code, String message, Throwable cause) {
    	super(message, cause);
    	this.code = code;
    }
    public ParserException(int code, Throwable cause) {
    	super(cause);
    	this.code = code;
    }
    public ParserException(int code) {
    	super();
    	this.code = code;
    }
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}