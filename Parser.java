import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {

  private final File file;

  public Parser(File file) {
  	this.file = file;
  }
  
  public File getFile() {
    return file;
  }

  public String getContent() throws IOException {
    return getContent(true);
  }

  public String getContentWithoutUnicode() throws IOException {
    return getContent(false);
  }

  protected String getContent(boolean withUnicode) throws IOException {
    BufferedInputStream i = new BufferedInputStream(new FileInputStream(file));
    try {
      StringBuilder output = new StringBuilder();
      int data;
      while ((data = i.read()) > 0) {
        if (withUnicode || data < 0x80) {
          output.append((char) data);
        }
      }
      return output.toString();
    } finally {
      i.close();
    }
  }


  public void saveContent(String content) throws IOException {
    BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file));
    try {
      o.write(content.getBytes());
    } finally {
      o.close();
    }
  }
}
