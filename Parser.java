import java.io.*;

public class Parser {

  private static final int ESTIMATED_FILE_SIZE = 10 * 1024;

  private final File file;

  public Parser(File file) {
    this.file = file;
  }

  public String getContent() throws IOException {
    StringBuilder content = new StringBuilder(ESTIMATED_FILE_SIZE);
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      boolean first = true;
      while ((line = reader.readLine()) != null) {
        if (!first) {
          content.append(System.lineSeparator());
        }
        first = false;
        content.append(line);
      }
    }
    return content.toString();
  }

  public synchronized void saveContent(String content) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write(content);
    }
  }

  public String getContentWithoutUnicode() throws IOException {
    return retainOnlyAscii(getContent());
  }

  protected final String retainOnlyAscii(String originalContent) {
    int estimatedSize = originalContent.length();
    StringBuilder filtered = new StringBuilder(estimatedSize);
    for (int i = 0; i < estimatedSize; i++) {
      char charAt = originalContent.charAt(i);
      if (charAt < 0x80) {
        filtered.append(charAt);
      }
    }
    return filtered.toString();
  }
}
