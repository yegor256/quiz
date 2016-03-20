import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private final File file;

  public Parser(File file) {
    validateFile(file);
    this.file = file;
  }

  public String getFile() {
    return file;
  }

  public String getContent() {
    return getContentFiltered((data) -> true);
  }

  public String getContentWithoutUnicode() {
    return getContentFiltered((data) -> data < 0x80);
  }

  public void saveContent(String content) {
    try {
      FileOutputStream o = new FileOutputStream(file);
      for (int i = 0; i < content.length(); i += 1) {
        o.write(content.charAt(i));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String getContentFiltered(Predicate<Integer> filter) {
    try {
      FileInputStream i = new FileInputStream(file);
      String output = "";
      int data;
      while ((data = i.read()) > 0) {
        if (filter.test(data)) {
          output += (char) data;
        }
      }
      return output;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void validateFile(File file) {
    if (file == null) throw new IllegalArgumentException("File can not be null");
    if (!file.isFile()) throw new IllegalArgumentException(
            String.format("Supplied file: %s does not exist or is not a file",
                    file.getAbsolutePath())
    );
  }
}
