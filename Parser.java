import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is thread safe.
 */
public class Parser {
  private static final Charset CHARSET = StandardCharsets.UTF_8;
  private static final Pattern REGEX_NON_ASCII = Pattern.compile("[^\\x00-\\x7F]");
  private final File file;

  public Parser(File file) {
    this.file = file;
  }

  public String getContent() throws IOException {
    return new String(Files.readAllBytes(file.toPath()), CHARSET);
  }

  public String getContentWithoutUnicode() throws IOException {
    return REGEX_NON_ASCII.matcher(getContent()).replaceAll("");
  }
  public void saveContent(String content) throws IOException {
    Files.write(file.toPath(), content.getBytes(CHARSET));
  }
}
