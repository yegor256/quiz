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
  //can have bufferizing, but it's out of scope for the moment
  public String getContent() throws IOException {
    try(FileReader i = new FileReader(file)) {
      StringWriter output = new StringWriter();
      int data;
      while ((data = i.read()) > 0) {
        output.write(data);
      }
      return output.toString();
    }
  }
  //can have bufferizing, but it's out of scope for the moment
  public String getContentWithoutUnicode() throws IOException {
    try(FileInputStream i = new FileInputStream(file)) {
      StringWriter output = new StringWriter();
      int data;
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output.write(data);
        }
      }
      return output.toString();
    }
  }
  //can have bufferizing, but it's out of scope for the moment
  public void saveContent(String content) throws IOException {
    try(FileWriter o = new FileWriter(file)) {
      o.write(content);
    }
  }
}
