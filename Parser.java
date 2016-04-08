import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {
  private final File file;

  public Parser(final File file) {
    validateFile(file);
    this.file = file;
  }

  private void validateFile(final File file) {
    if (file == null)
      throw new IllegalArgumentException("File can't be null");

    if (!file.exists())
      throw new IllegalArgumentException("File does not not exists");

    if (!file.isFile())
      throw new IllegalArgumentException("File must be normal file");
  }

  public synchronized String getContent() throws IOException {
    return getContent(false);
  }

  public synchronized String getContentWithoutUnicode() throws IOException {
    return getContent(true);
  }

  private String getContent(boolean withoutUnicode) throws IOException {
    StringBuilder sb = new StringBuilder();
    int data;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      while ((data = br.read()) > 0) {
        if (!withoutUnicode || data < 0x80) {
          sb.append((char) data);
        }
      }
    }

    return sb.toString();
  }

  public synchronized void saveContent(String content) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write(content);
    }
  }
}
