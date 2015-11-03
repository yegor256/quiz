import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
  protected void checkFileType() throws IOException {
    if (!file.isFile()) {
      throw new IOException("file: " + file.getName() + ", is not a regular file");
    }
  }
  protected void checkFileSize() throws IOException {
	if (file.length() > Integer.MAX_VALUE) {
	  throw new IOException("contents of file: " + file.getName() + ", too large to parse (" + String.valueOf(file.length()) + " bytes)");
	}
  }
  protected synchronized FileInputStream getInputStream() throws IOException {
	this.checkFileType();
	this.checkFileSize();
	return new FileInputStream(file);
  }
  protected synchronized FileOutputStream getOutputStream() throws IOException {
    this.checkFileType();
    return new FileOutputStream(file);
  }
  public String getContent() throws IOException {
	FileInputStream input = this.getInputStream();
    StringBuffer buffer = new StringBuffer((int)file.length());
    int data;
    while ((data = input.read()) > 0) {
      buffer.append(data);
    }
    input.close();
    return buffer.toString();
  }
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream input = this.getInputStream();
    StringBuffer buffer = new StringBuffer((int)file.length());
    int data;
    while ((data = input.read()) > 0) {
      if (data < 0x80) {
        buffer.append(data);
      }
    }
    input.close();
    return buffer.toString();
  }
  public void saveContent(String content) throws IOException {
    FileOutputStream output = this.getOutputStream();
    for (int i = 0; i < content.length(); i += 1) {
      output.write(content.charAt(i));
    }
    output.close();
  }
  /* self-test */
  public static void main(String[] args) {
	Date start = new Date();
	try {
	  Parser parser = new Parser();
	  if (args.length == 1) { // test reading the file
		String fileName = args[0];
		File file = new File(fileName);
		parser.setFile(file);
		String contents = parser.getContent();
		System.err.println( "Parser: contents " + String.valueOf(contents.length()) + " bytes");
		String withoutUnicode = parser.getContentWithoutUnicode();
		System.err.println( "Parser: contents without unicode " + String.valueOf(withoutUnicode.length()) + " bytes");
	  }
	  else if (args.length == 2) { // test writing the file
		String fileName = args[0];
		String fileText = args[1];
		File file = new File(fileName);
		parser.setFile(file);
		parser.saveContent( fileText);
		String contents = parser.getContent();
		System.err.println( "Parser: wrote contents " + String.valueOf(contents.length()) + " bytes");
		String withoutUnicode = parser.getContentWithoutUnicode();
		System.err.println( "Parser: written contents without unicode " + String.valueOf(withoutUnicode.length()) + " bytes");
	  }
	  else {
		System.err.println( "Parser: no filename provided.\n\tusage: java Parser filename [contents]");
	  }
	}
	catch (Exception except) {
	  System.err.println( "Parser: caught " + except.toString());
	  except.printStackTrace(System.err);
	}
	Date done = new Date();
	long elapsed = done.getTime() - start.getTime();
	System.err.println( "Parser: elapsed " + String.valueOf(elapsed) + " ms");
  }
}
