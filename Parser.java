import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * \brief Parse a file and makes it possible to return its content in a String.
 * \todo Make this class thread safe. (Constructors cannot be 'synchronize' for instance.)
 */
public class ParsedFile {

  private File file;

  public ParsedFile(File f) {
    file = f;
    // \TODO: Load content at construction time to make it even more immutable?
  }

  public synchronized File getFile() {
    return file;
  }
  
  private synchronized getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    return output;
  }

  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    return output;
  }

  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }

}
