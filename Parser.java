import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {

  // should be at least one empty line between each methods

  // it might be a good idea to make indent 4 spaces

  // removed file variable, also related getter / setter, as it is should not the part of Parser

  // file to be passed as parameter
  public String getContent(File file) throws IOException {
    String output = "";
    // missing try with resources
    // use of single charcter variable should be restricted
    try (FileInputStream i = new FileInputStream(file)){
      int data;
      // check should be for >= 0 for eof
      while ((data = i.read()) >= 0) {
        // use StringBuilder here
        output += (char) data;
      }
    }
    return output;
  }

  // file to be passed as parameter
  public String getContentWithoutUnicode(File file) throws IOException {
    // missing try with resources - as shown in getContent() above
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    // check should be for >= 0 for eof
    while ((data = i.read()) >= 0) {
      if (data < 0x80) {
        // use StringBuilder here
        output += (char) data;
      }
    }
    return output;
  }

  // file to be passed as parameter
  public void saveContent(File file, String content) throws IOException {
    // missing try with resources - as shown in getContent() above
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
