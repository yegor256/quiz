import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;

/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  private String content;
  private String contentWithoutUnicode;
  public synchronized void setFile(File f) throws IOException {
    file = f;
    if (file != null) {
      Path filePath = file.toPath();
      content = new String(Files.readAllBytes(filePath));
      contentWithoutUnicode = content.replaceAll("[^\\x00-\\x7F]", "");
    } else {
      content = null;
      contentWithoutUnicode = null;
    }
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent() {
    return content;
  }
  public String getContentWithoutUnicode() {
    return contentWithoutUnicode;
  }
  public void saveContent(String content) throws IOException {
    if (file != null) {
      Files.write(file.toPath(), content.getBytes());
    }
  }
}