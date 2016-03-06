import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 *
 * @author tiborkovacs
 */
public class Parser {
  private File file;

  /**
   * Setter method, to set the used file.
   *
   * @param f  the file which will be stored
   */
  public synchronized void setFile(File f) {
    file = f;
  }

  /**
   * Getter method, to get the used file.
   *
   * @return  Return the stored file
   */
  public synchronized File getFile() {
    return file;
  }

  /**
   * Returns with the content of the set file.
   *
   * @return  Return String content of the file
   * @throws IOException  Exception will be thrown if the file is not exists, or not readable
   */
  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    return output;
  }

  /**
   * Returns with the content of the set file, without any Unicode characters.
   *
   * @return  Return String content of the file, without Unicode characters
   * @throws IOException  Exception will be thrown if the file is not exists, or not readable
   */
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

  /**
   * Saves the given <b>content</b> to the file.
   *
   * @param content  This content will be written to the set file
   * @throws IOException  Exception will be thrown if the file is not writable
   */
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }

}