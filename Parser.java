import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {

  public static String parse(File file, Boolean includeUTF) throws IOException {
    return readFileContent(file, includeUTF);
  }

  private static String readFileContent(File file, Boolean includeUTF) throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (includeUTF) {
        output += (char) data;
      } else if(data < 0x80) {
        output += (char) data;
      }
    }
    return output;
  }
}
