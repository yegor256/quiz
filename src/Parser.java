import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Parser {

  private final String content;
  public Parser(String content) {
    this.content = content;
  }

  public Parser(File file) throws IOException {
    this(new String(Files.readAllBytes(file.toPath())));
  }

  public Parser(File file, Charset charset) throws IOException {
    this(new String(Files.readAllBytes(file.toPath()), charset));
  }

  public String getContentWithoutUnicode() throws IOException {
    return content.replaceAll("\\P{Print}", "");
  }
}