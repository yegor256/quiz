
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  
  /**
   * gets content of Parser's file
   * @return a string that representation of file content
   * @throws IOException
   */
  public String getContent() throws IOException {
	// null check to avoid null pointer exception
	if(file==null){
		return null;
	}
	String output = "";
    /*
     * Old Code
    FileInputStream fileInputStream = new FileInputStream(file);
    int data;
    while ((data = fileInputStream.read()) > 0) {
      output += (char) data;
    }*/
    // replacing two things
	// 1. instead of creating lot of string instances we create one string builder
	// 2. use try with resource in order to handle stream close properly
    try(BufferedReader br = new BufferedReader(new FileReader(file))) {
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        output = sb.toString();
    }
    return output;
  }
  // since we have two methods that essentially do same thing we also can combine it to one method and user overloading
  
  /**
   * gets file content while ignoring unicode characters
   * @return unicode characters skipped string from file content
   * @throws IOException
   */
  public String getContentWithoutUnicode() throws IOException {
	// null check to avoid null pointer exception
	if(file==null){
		return null;
	}
	String output = "";
	// same here just added try with resource
	try(FileInputStream i = new FileInputStream(file)) {
	    int data;
	    while ((data = i.read()) > 0) {
	      if (data < 0x80) {
	        output += (char) data;
	      }
	    }
	}
    return output;
  }
  
  /**
   * Saves given string to a local file object
   * @param content string that need to be written into file
   * @throws IOException
   */
  public void saveContent(String content) throws IOException {
    /* Old code
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }*/
    
    // easier and better way to write string into file
	// also synchronized because Parser class meant to be thread safe
	synchronized (file) {
		try(PrintWriter out = new PrintWriter(file)){
	        out.println(content);
	    }
	}
  }
}
