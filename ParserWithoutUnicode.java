import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class ParserWithoutUnicode implements FileParser {
  private final File file;
  public ParserWithoutUnicode(final File file) {
      this.file = file;
  }
  public synchronized File file() {
        return file;
    }
  public String content() throws IOException {
      final FileInputStream i = new FileInputStream(this.file);
      final StringBuilder output = new StringBuilder();
      int data;
      while ((data = i.read()) > 0) {
          if (data < 0x80) {
              output.append((char) data);
          }
      }
      return output.toString();
  }
  public void saveContent(final String content) throws IOException {
      final FileOutputStream o = new FileOutputStream(this.file);
      for (int i = 0; i < content.length(); i += 1) {
          o.write(content.charAt(i));
      }
  }
}
