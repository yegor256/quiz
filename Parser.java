import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {

  public static String getContent(File file) throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output.append((char) data);
      }
    } 
    i.close();
    return output.toString();
  }
  public static String getContentWithoutUnicode(File file) throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output.append((char) data);
      }
    } 
    i.close();
    return output.toString();
  }
  public static void saveContent(String content,File file) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    byte b[]=content.getBytes();
    o.write(b);  
    o.close();
  }
}
