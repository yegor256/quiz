import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  /**
   * Reads and return file content
   * @param file
   * @return
   * @throws IOException
   */
  public static String getContent(File file) throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    return output;
  }

  /**
   * Reads and returns file content without unicode characters
   * @param file
   * @return
   * @throws IOException
   */
  public static String getContentWithoutUnicode(File file) throws IOException {
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
   * Adds content to a file
   * @param file
   * @param content
   * @throws IOException
   */
  public static void saveContent(File file, String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
