import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
/**
 * This class is thread safe.
 */
public class Parser {
  private final File file;
  public Parser(File file) {
    this.file = file;
  }
  public File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    return new String(Files.readAllBytes(file.toPath()));
  }
  public String getContentWithoutUnicode() throws IOException {
    StringBuilder sb = new StringBuilder();
    for (char c : getContent().toCharArray()) {
      if (c < 0x80) {
        sb.append(c);
      }
    }
    return sb.toString();
  }
  public void saveContent(String content) throws IOException {
    try (FileOutputStream o = new FileOutputStream(file)) {
      synchronized (file) {
        o.write(content.getBytes());
      }
    }
  }
}
