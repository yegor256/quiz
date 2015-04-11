import java.io.*;

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
  public synchronized String getContent() throws IOException {
    return getContent(true);
  }
  public synchronized String getContentWithoutUnicode() throws IOException {
    return getContent(false);
  }

  private String getContent(boolean unicodeEnabled) throws IOException {
      try(InputStream is = new BufferedInputStream(new FileInputStream(file))) {
          StringBuilder stringBuilder = new StringBuilder();
          int data;
          while ((data = is.read()) > 0) {
              if (unicodeEnabled || data < 0x80) {
                  stringBuilder.append((char) data);
              }
          }
          return stringBuilder.toString();
      }
  }

  public synchronized void saveContent(String content) throws IOException {
    try(OutputStream os = new BufferedOutputStream(new FileOutputStream(file))) {
        for (int i = 0; i < content.length(); i += 1) {
            os.write(content.charAt(i));
        }
    }
  }
}
