import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  //I would remove the setter/getter for file and make it a parameter to the constructor.
  //If a user needs to change the file, it should require a new parser.
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }

  public String getContent() throws IOException {
    byte[] bytes = Files.readAllBytes(file.toPath());
    return new String(bytes);
  }
  //TODO: implement a FilteredInputStream that takes a lambda for which characters to skip
  //this would allow alternate cases to be easily implemented and use that stream to read
  //the character. Also would use a stringbuffer instead of string concat (more performant)
  public String getContentWithoutUnicode() throws IOException {
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
  public void saveContent(String content) throws IOException {
      PrintWriter writer = new PrintWriter(file);
      writer.print(content);
  }
}
