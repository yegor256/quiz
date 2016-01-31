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

  public String getContent() throws IOException {
    final StringBuffer stringBuffer = new StringBuffer();
    final BufferedReader reader = getReader();

    String output = null;
    while((output = reader.readLine()) != null) {
      stringBuffer.append(output);
    }
    return stringBuffer.toString();
  }

  public String getContentWithoutUnicode() throws IOException {
    final BufferedReader reader = getReader();
    final StringBuffer stringBuffer = new StringBuffer();

    int data;
    while((data = reader.read()) > 0) {
      if(data < 0x80) {
        stringBuffer.append((char)data);
      }
    }

    return stringBuffer.toString();
  }

  public void saveContent(String content) throws IOException {
    final FileOutputStream o = new FileOutputStream(getFile());
    o.write(content.getBytes());
  }

  private BufferedReader getReader() throws IOException {
    final FileInputStream i = new FileInputStream(getFile());
    final BufferedReader reader = new BufferedReader(new InputStreamReader(i));

    return reader;
  }

}