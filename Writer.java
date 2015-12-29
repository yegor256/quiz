import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe and immutable.
 */
public class Writer {

  private final File file;

  /**
   * Creates a new writer instance. Cool immutable and thread safe
   * writer class that should help us to use SOLID principles.
   * @param file file that you probably want to parse.
   */
  public Writer(File file) {
    this.file = file;
  }

  /**
   * Write content to the end of the file.
   *
   * @param content content to wright
   * @throws java.io.IOException
   */
  public void appendContentToFile(String content) throws IOException {
    try (FileOutputStream o = new FileOutputStream(file)) {
      o.write(content.getBytes());
    }
  }

  public File getFile() {
    return file;
  }
}
