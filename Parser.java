import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  private static final int HEX_80 = 0x80;

  /**
   * Constructor which supplies the file to the Parser object.
   * @param file - the file that this Parser works with.
   */
  public Parser(File file) {
	this.file = file;
  }

  /**
   * Constructor which looks for the file at the specified path.
   * @param filePath - the path to the file that this Parser works with.
   */
  public Parser(String filePath) {
	this.file = new File(filePath);
  }
  
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    FileInputStream inputStream = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = inputStream.read()) > 0) {
      output.append((char) data);
    }
    
    inputStream.close();
    return output.toString();
  }
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream inputStream = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = inputStream.read()) > 0) {
      if (data < HEX_80) {
        output.append((char) data);
      }
    }
    inputStream.close();

    return output.toString();
  }
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream outputStream = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i++) {
      outputStream.write(content.charAt(i));
    }
    outputStream.close();
  }
}
