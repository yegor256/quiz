import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.regex.Pattern;

public final class Parser {
  private static final Pattern NON_ASCII = Pattern.compile("[^\\x00-\\x7F]");

  private final Path path;

  public Parser(Path path) {
    this.path = path;
  }

  public String getContent() throws IOException {
    return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
  }

  public String getContentWithoutUnicode() throws IOException {
    return NON_ASCII.matcher(getContent()).replaceAll("");
  }

  public void saveContent(String content) throws IOException {
    Files.write(path, content.getBytes(), StandardOpenOption.CREATE);
  }
}