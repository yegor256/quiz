import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class FileUtility {

  private final File file;

  public FileUtility(final File file) {
    if (file == null)
      throw new IllegalArgumentException("File can't be null");

    if (!file.exists())
      throw new IllegalArgumentException("File does not not exists");

    if (file.isDirectory())
      throw new IllegalArgumentException("It can't be a directory");

    this.file = file;
  }

  public synchronized String getContent(boolean isUnicode) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    int data;
    StringBuilder sb = new StringBuilder();
    while ((data = br.read()) > 0) {
      if (isUnicode) {
        if(data < 0x80)
          sb.append((char) data);
        continue;
      }
      sb.append((char) data);
    }
    br.close();
    return sb.toString();
  }

  public synchronized void saveContent(String content) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(file)); 
    writer.write(content);
    writer.close();
  }
}