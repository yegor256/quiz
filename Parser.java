import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {

  public static final int LENGTH = 0x80;

  private ThreadLocal<File> file = new ThreadLocal<File>();

  public void setFile(File f) {
    file.set(f);
  }

  public File getFile() {
    return file.get();
  }

  public final String getContentWithoutUnicode() throws IOException {
    return getContent(Boolean.TRUE);
  }

  public final String getContent() throws IOException {
    return getContent(Boolean.FALSE);
  }

  public final void saveContent(String content) throws IOException {
    saveContent(content, "UTF8");
  }

  protected String getContent(Boolean excludeUnicode) throws IOException {
    FileInputStream i = new FileInputStream(file.get());
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      char charToWrite = (char) data;
      if (excludeUnicode) {
        if (data < LENGTH) {
          output.append(charToWrite);
        }
      } else {
        output.append(charToWrite);
      }
    }
    i.close();
    return output.toString();
  }

  protected void saveContent(String content, String enc) throws IOException {
    BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.get()), enc));
    buff.append(content);
    buff.flush();
    buff.close();
  }
}