import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
  private String getContent(Appender a) throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      a.append(output, data);
    }
    i.close();
    return output.toString();
  }
  public synchronized String getContent() throws IOException {
    return getContent(new SimpleAppender());
  }

  public synchronized String getContentWithoutUnicode() throws IOException {
    return getContent(new WithoutUnicodeAppender());
  }
  
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
    o.close();
  }
  
  interface Appender{
    void append(StringBuilder sb, int data);
  }
  
  class SimpleAppender implements Appender{
	public void append(StringBuilder sb, int data) {
      sb.append((char) data);
    }
  }
  
  class WithoutUnicodeAppender implements Appender{
    public void append(StringBuilder sb, int data) {
      if(data < 0x80){
	    sb.append((char) data);
	  }
    }
  }
}
