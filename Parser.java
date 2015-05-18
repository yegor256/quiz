import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

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
    BufferedReader reader = new BufferedReader(new FileReader(file));
    StringBuilder output = new StringBuilder();
    String line = null;
    
    while ((line = reader.readLine()) != null) {
      output.append(line);
      output.append("\n")
    }
    reader.close();
    return output.toString();
  }
  public String getContentWithoutUnicode() throws IOException {
    FileReader reader = new FileReader(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
         output.append((char) data);
      }
    }
    reader.close();
    return output.toString();
  }
  public void saveContent(String content) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    writer.write(content);
    writer.close();
  }
}
