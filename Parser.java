import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * This class is thread safe.
 */
public class Parser {
  private volatile File file;
  public void setFile(File f) {
    if (f == null) {
      throw new IllegalArgumentException("Cannot set file to null.");
    }
    file = f;
  }
  public File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    byte[] contents = Files.readAllBytes(getFile().toPath());
    return new String(contents, StandardCharsets.US_ASCII);
  }
  public String getContentWithoutUnicode() throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(getFile().toPath(), StandardCharsets.US_ASCII)) {
      StringBuilder builder = new StringBuilder();
      int read;
      while ((read = reader.read()) != -1 && read < 128) {
        builder.append((char) read);
      }
      return builder.toString();
    }
  }
  public void saveContent(String content) throws IOException {
    Files.write(getFile().toPath(), content.getBytes(StandardCharsets.US_ASCII));
  }
}
