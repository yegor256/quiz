import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is thread safe.
 */
public class Parser {
  private static final int HEXADECIMAL128 = 0x80;

  private File file;
  private String content;
  private String contentWithoutUnicode;

  private Parser(){

  }
  private void initContentWithoutUnicode() throws IOException {
    FileInputStream inputStream = null;
    String output = "";
    try {
      inputStream = new FileInputStream(file);

      int data;
      while ((data = inputStream.read()) > 0) {
        if (data < HEXADECIMAL128) {
          output += (char) data;
        }
      }
      contentWithoutUnicode = output;

    }
    finally {
      if(inputStream != null)
        inputStream.close();
    }

  }
  private void initContent() throws IOException {
    content = new String(Files.readAllBytes(Paths.get(file.toURI())));
  }
  public Parser(File file) throws IOException {
    this.file = file;
    initContent();
    initContentWithoutUnicode();
  }

  public File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    return content;

  }
  public String getContentWithoutUnicode() throws IOException {
    return contentWithoutUnicode;

  }
  public synchronized void saveContent(String content) throws IOException {
    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file)))) {
      writer.write(content);
    }
  }
}
