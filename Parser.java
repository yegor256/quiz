import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {
  private static final int UNICODE_LIMIT = 0x80;
  private File file;

  public synchronized void setFile(File file) {
    this.file = file;
  }

  public synchronized File getFile() {
    return file;
  }

  public String getContent() throws IOException {
    return readAndFilter(-1);
  }

  public String getContentWithoutUnicode() throws IOException {
    return readAndFilter(UNICODE_LIMIT);
  }

  private String readAndFilter(int limit) throws IOException {
    FileInputStream inputStream = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = inputStream.read()) > 0) {
      if (limit < 0 || data < limit) {
        output += (char) data;
      }
    }
    return output;
  }

  public void saveContent(String content) throws IOException {
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
    writer.write(content);
    writer.close();
  }
}
