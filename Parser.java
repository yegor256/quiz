import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
  public String getContent() throws IOException {
	// TODO bdragan 2015-09-19: Close the stream
	// TODO bdragan 2015-09-19: Synchronize access to the file field
    BufferedReader reader = new BufferedReader(new FileReader(file));
    StringBuilder output = new StringBuilder();
    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
      output.append(line);
    }
    return output.toString();
  }
  public String getContentWithoutUnicode() throws IOException {
    String content = getContent();
    StringBuilder output = new StringBuilder(content.length());
    for (int i = 0; i < content.length(); i++) {
      char data = content.charAt(i);
      if (data < 0x80) {
        output.append(data);
      }
    }
    return output.toString();
  }
  public void saveContent(String content) throws IOException {
	// TODO bdragan 2015-09-19: Close the stream
	// TODO bdragan 2015-09-19: Synchronize access to the file field  
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    writer.write(content);
  }
}
