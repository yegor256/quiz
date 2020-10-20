import java.io.*;
import java.util.stream.IntStream;

public class Parser {

  public final File file;
  private final FileInputStream inputStream;

  public Parser(File file) throws FileNotFoundException {
    if (file == null) throw new FileNotFoundException("input file is null");
    this.file = file;
    this.inputStream = new FileInputStream(file);
  }

  public void saveContent(String content) throws IOException {
    var outputStream = new FileOutputStream(this.file);
    for (int i : IntStream.range(0, content.length()).toArray()) {
      outputStream.write(content.charAt(i));
    }
    outputStream.close();
  }

  public String getContent() throws IOException {
    return this.iterateOverContent(false);
  }

  public String getContentWithoutUnicode() throws IOException {
    return this.iterateOverContent(true);
  }

  private String iterateOverContent(boolean unicode) throws IOException {
    var builder = new StringBuilder();
    int content;
    while ((content = this.inputStream.read()) > 0) {
      this.contentAsChar(unicode, builder, content);
    }
    this.inputStream.close();
    return builder.toString();
  }

  private void contentAsChar(boolean unicode, StringBuilder builder, int content) {
    if (unicode) {
      if (content < 0x80) {
        builder.append((char) content);
      }
    } else {
      builder.append((char) content);
    }
  }

}
