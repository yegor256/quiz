import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Parser {
  private File file;
  private static int UNICODE_IDENTIFIER = 0x80;

  public final synchronized void setFile(File f) {
    file = f;
  }

  public final synchronized File getFile() {
    return file;
  }

  public final String getContent() throws IOException {
    synchronized (file) {
      StringBuilder builder = new StringBuilder();
      try (FileInputStream i = new FileInputStream(file)) {
        int data;
        while ((data = i.read()) > 0) {
          builder.append((char) data);
        }
      }
      return builder.toString();
    }
  }

  public final String getContentWithoutUnicode(File file) throws IOException {
    synchronized (file) {
      StringBuilder builder = new StringBuilder();
      try (FileInputStream i = new FileInputStream(file)) {
        int data;
        while ((data = i.read()) > 0) {
          if (data < UNICODE_IDENTIFIER) {
            builder.append((char) data);
          }
        }
      }
      return builder.toString();
    }
  }

  public final void saveContent(String content) throws IOException {
    synchronized (file) {
      try (FileOutputStream o = new FileOutputStream(file)) {
        for (int i = 0; i < content.length(); i += 1) {
          o.write(content.charAt(i));
        }
      }
    }
  }
}
