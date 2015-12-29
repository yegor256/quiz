import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is thread safe and immutable.
 */
public class Parser {

  private final File file;

  /**
   * Creates a new parser instance. Best parser in the world that can be useful
   * for refactoring quiz and parse some content.
   * @param file file that you probably want to parse.
   */
  public Parser(@NotNull File file) {
    this.file = file;
  }

  /**
   * Get file content in a string format.
   *
   * @return String content of the file.
   * @throws IOException
   */
  public String readContent() throws IOException {
    try (FileInputStream inputStream = new FileInputStream(file)) {
      StringBuilder output = new StringBuilder();
      int data;
      while ((data = inputStream.read()) > 0) {
        output.append((char) data);
      }
      return output.toString();
    }
  }

  /**
   * Get file content without unicode in a string format.
   *
   * @return String content of the file.
   * @throws IOException
   */
  public String readContentWithoutUnicode() throws IOException {
    try (FileInputStream inputStream = new FileInputStream(file)) {
      StringBuilder output = new StringBuilder();
      int data;
      while ((data = inputStream.read()) > 0) {
        if (data < 0x80) {
          output.append((char) data);
        }
      }
      return output.toString();
    }
  }

  public File getFile() {
    return file;
  }
}