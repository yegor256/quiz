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
  
  public String getContent() throws IOException {
    Scanner scanner = new Scanner(file);
    String content = scanner.useDelimiter("\\A").next();
    scanner.close();
    return content;
  }
  
  public String getContentWithoutUnicode() throws IOException {
    return getContent().replaceAll("[\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}]", "");
  }
  
  public void saveContent(String content) throws IOException {
  	new PrintStream(new FileOutputStream(file)).print(content);
  }
}