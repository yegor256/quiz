import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    return new String(Files.readAllBytes(Paths.get(file.toURI())));

  }
  public synchronized String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    return output;
  }
  public  synchronized void saveContent(String content) throws IOException {
    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file)))) {
      writer.write(content);
    }
  }
}
