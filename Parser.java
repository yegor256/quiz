import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class is thread safe.
 */
public class Parser {

  public String getContent(Path file) throws IOException {
    try (BufferedReader i = Files.newBufferedReader(file)) {
      StringBuilder output = new StringBuilder();
      String data;
      while ((data = i.readLine()) != null) {
        output.append(data).append('\n');
      }
      return output.toString();
    }
  }

  public String getContentWithoutUnicode(Path file) throws IOException {
    FileInputStream i = null;
    try {
      i = new FileInputStream(file.toFile());
      String output = "";
      int data;
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output += (char) data;
        }
      }
      return output;
    } finally {
      if (i != null) {
        i.close();
      }
    }
  }

  public void saveContent(Path file, String content) throws IOException {
    try (BufferedWriter o = Files.newBufferedWriter(file)) {
      for (int i = 0; i < content.length(); i++) {
        o.write(content.charAt(i));
      }
    }
  }
}
