import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

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
    return new String(Files.readAllBytes(file.toPath()));
  }

  public String getContentWithoutUnicode() throws IOException {
    return getContent().replaceAll("[^\\x00-\\x7F]", ""); // remove all non-ascii characters
  }

  public void saveContent(String content) throws IOException {
    Files.write(file.toPath(), content.getBytes(), StandardOpenOption.APPEND);
  }

  public static void main(String[] args) throws IOException {
    Parser p = new Parser(new File("D:\\work\\quiz\\README.md"));
    System.out.println(p.getContentWithoutUnicode());
  }
}
