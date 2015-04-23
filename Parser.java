import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    try (FileInputStream i = new FileInputStream(file);
    	  InputStreamReader sr = new InputStreamReader(i, "UTF-8")) {
      StringBuilder output = new StringBuilder();
      char[] data = new char[4096];
      int read = 0;

      while ((read = sr.read(data)) != -1) {
        output.append(data, 0, read);
      }
      return output.toString();
    }
  }
  public String getContentWithoutUnicode() throws IOException {
    String contentWithUnicode = getContent();
    StringBuilder filtered = new StringBuilder();
    for(char ch : contentWithUnicode.toCharArray()) {
      if(ch < 0x80) {
    	filtered.append(ch);
      }
    }
    return filtered.toString();
  }

  public void saveContent(String content) throws IOException {
    try (FileOutputStream o = new FileOutputStream(file);
    	  OutputStreamWriter sw = new OutputStreamWriter(o, "UTF-8")) {
      sw.append(content);
    }
  }
}
