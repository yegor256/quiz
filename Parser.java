import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    return output;
  }
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    return output;
  }

  /**
   * Saves {@code content} to file as UTF-8 encoded text. Truncates file if already exists.
   * @param content text to save
     */
  public synchronized void saveContent(final String content) {
    if (content == null) {
      throw new IllegalArgumentException("content could not be null");
    }
    try (final BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
      writer.write(content, 0, content.length());
    } catch (IOException ex) {
      throw new ParsingException(ex);
    }
  }

  public static class ParsingException extends RuntimeException {
    public ParsingException(final Throwable cause) {
      super(cause);
    }
  }
}
