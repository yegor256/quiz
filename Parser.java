import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {

  public static String getContent(File file) throws IOException {
    try(BufferedReader i = new BufferedReader(new FileReader(file))){
      StringBuffer output = new StringBuffer();
      int data;
      while ((data = i.read()) > 0) {
        output.append((char) data);
      }
      return output;
    }
  }
  public static String getContentWithoutUnicode(File file) throws IOException {
    try(FileInputStream i = new FileInputStream(file)){
      StringBuffer output = new StringBuffer();
      int data;
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output.append((char) data);
        }
      }
      return output.toString();
    }
  }
  public static void saveContent(String content, File file) throws IOException {
    try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
      writer.write(content);
      writer.flush();
    }
  }
}
