import java.io.*;

/**
 * This class is not thread safe.
 */
public class Parser {
  private static final int UNICODE_MARKER = 0x80;
  private File file;

  public synchronized void setFile(File f) {
    file = f;
  }

  public synchronized File getFile() {
    return file;
  }

  public synchronized String getContent() throws IOException {
    return getContent(file, true);
  }

  private static String getContent(File file, boolean readUnicode) throws IOException {
    String output = "";
    int data;

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      while ((data = reader.read()) > 0) {
        if (readUnicode || isNotUnicode(data)) {
          output += (char) data;
        }
      }
      return output;
    }
  }

  private static boolean isNotUnicode(int charCode) {
    return charCode < UNICODE_MARKER;
  }

  public synchronized String getContentWithoutUnicode() throws IOException {
    return getContent(file, false);
  }

  public synchronized void saveContent(String content) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write(content);
    }
  }
}
