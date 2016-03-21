import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  private static final String NON_ASCII = "[^\\x00-\\x7F]";
  private static final Charset charset = StandardCharsets.UTF_8;
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    Path path = file.toPath();
    byte[] encoded = Files.readAllBytes(path);
    return new String(encoded, charset);
  }
  public String getContentWithoutUnicode() throws IOException {
    String content = this.getContent();
    return content.replaceAll(NON_ASCII, "");
  }
  public void saveContent(String content) throws IOException {
    BufferedWriter bw = Files.newBufferedWriter(file.toPath(), charset, StandardOpenOption.TRUNCATE_EXISTING);
    bw.write(content);
    bw.flush();
  }
}
