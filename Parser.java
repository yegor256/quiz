import java.io.*;

/**
 * This class is thread safe.
 * This class is now immutable (read only) and obviously is thread safe.
 */
public class Parser {
  private final File file;

  public Parser(File f) {
    file = f;
  }

  public File getFile() {
    return file;
  }

  public String getContent() {
      try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
          StringBuilder output = new StringBuilder();
          int data;
          while ((data = in.read()) > 0) {
              output.append((char) data);
          }
          return output.toString();
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }

  public String getContentWithoutUnicode() {
      try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
          StringBuilder output = new StringBuilder();
          int data;
          while ((data = in.read()) > 0) {
              if (data < 0x80) {
                  output.append((char) data);
              }
          }
          return output.toString();
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }

  public void saveContent(String content) {
      if (content != null) {
          try (FileOutputStream o = new FileOutputStream(file)) {
              o.write(content.getBytes("UTF-8"), 0, content.length());
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      }
  }
}
