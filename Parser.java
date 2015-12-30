import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
  private final File file;

  public Parser(final File file) {
    this.file = file;
  }

  public File getFile() {
    return file;
  }

  public synchronized String getContent() throws IOException {
    return getContent(false);
  }

  public synchronized String getContentWithoutUnicode() throws IOException {
    return getContent(true);
  }

  public synchronized String getContent(boolean withUnicode) throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder("");
    int data;
    while ((data = i.read()) > 0) {
      if (withUnicode) {
        if (unicodeFilter(data)) {
          output.append((char) data);
        }
      } else {
        output.append((char) data);
      }
    }
    return output.toString();
  }

  private boolean unicodeFilter(int data) {
    return data < 0x80;
  }

  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    try {
      for (int i = 0; i < content.length(); i += 1) {
        fileOutputStream.write(content.charAt(i));
      }
    } finally {
      fileOutputStream.close();
    }
  }
}
