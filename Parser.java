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
	/*
	 * Thread-safe file setter
	 */
    file = f;
  }

  public synchronized File getFile() {
	/*
	 * Thread-safe file getter
	 */
    return file;
  }

  public String getContent(boolean withUnicode) throws IOException {
	/*
	 * Read the contents of file and return it as string with or without unicode
	 */
    FileInputStream fileInputStream = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = fileInputStream.read()) > 0) {
	  if (withUnicode || data < 0x80){
      	output += (char) data;
	  }
    }
	fileInputStream.close();
    return output;
  }

  public String getContent() throws IOException{
	/*
	 * Read contents of file with unicodes and return it as string
	 */
	return getContent(true);
  }

  public String getContentWithoutUnicode() throws IOException {
	/*
	 * Read contents of file without unicodes and return it as string
	 */
	return getContent(false);
  }

  public void saveContent(String content) throws IOException {
	/*
	 * Save the String content to the file
	 */
    FileOutputStream fileOutputStream = new FileOutputStream(file);
	byte contentBytes[] = content.getBytes();
	fileOutputStream.write(contentBytes);
	fileOutputStream.close();
  }
}
