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

  /**
   * Retrieves content from the file
   *
   * @return String with file's content
   * @throws IOException
   */
  public synchronized String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      output.append((char) data);
    }
    i.close();
    return output.toString();
  }

  /**
   * Retrieves content from the file without unicode characters
   *
   * @return String with file's conent
   * @throws IOException
   */
  public synchronized String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output.append((char) data);
      }
    }
    return output.toString();
  }

  /**
   * Saves content to the file
   *
   * @param content String with file's content
   * @throws IOException
   */
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
    o.close();
  }
}
