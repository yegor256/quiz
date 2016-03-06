import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

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
    BufferedReader bufferedReader = _getBufferedReader();

    StringBuffer stringBuffer = new StringBuffer((int) file.length());

    try {
      int data;

      while ((data = bufferedReader.read()) > 0) {
        stringBuffer.append((char) data);
      }
    }
    finally {
      bufferedReader.close();
    }

    return stringBuffer.toString();
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

  /**
   * Returns with a <code>BufferedReader</code> to be able to read the content of the set file.
   *
   * @return  Returns with a <code>BufferedReader</code>
   * @throws  FileNotFoundException  Exception will be thrown if the file is not exists
   */
  private BufferedReader _getBufferedReader() throws FileNotFoundException {
    FileInputStream fis = new FileInputStream(file);

    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));

    return new BufferedReader(isr);
  }

}