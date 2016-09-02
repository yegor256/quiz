import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {

  public static final int DEFAULT_BUFFER_SIZE = 1024 * 16;
  public static final int ASCII_LOWER_BOUND = 0x2F;
  public static final int ASCII_UPPER_BOUND = 0x80;

  private File file;

  public synchronized void setFile(File file) {
    this.file = file;
  }

  public synchronized File getFile() {
    return file;
  }

  public synchronized String getContent() throws IOException {
    FileInputStream fileInputStream = new FileInputStream(file);
    StringBuilder result = new StringBuilder();
    try {
      Reader reader = new BufferedReader(new InputStreamReader(fileInputStream));
      char[] buffer = new char[DEFAULT_BUFFER_SIZE];
      int read;
      while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
        result.append(buffer, 0, read);
      }
    } finally {
      fileInputStream.close();
    }

    return result.toString();
  }

  public synchronized String getContentWithoutUnicode() throws IOException {
    FileInputStream fileInputStream = new FileInputStream(file);
    StringBuilder result = new StringBuilder();
    try {
      Reader reader = new BufferedReader(new InputStreamReader(fileInputStream));
      char[] buffer = new char[DEFAULT_BUFFER_SIZE];
      int read;
      while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
        for (int i = 0; i < read; i++) {
          int charCode = (int) buffer[i];
          if (charCode > ASCII_LOWER_BOUND && charCode < ASCII_UPPER_BOUND) { // char code between 31 and 128
            result.append(buffer[i]);
          }
        }
      }
    } finally {
      fileInputStream.close();
    }

    return result.toString();
  }

  public synchronized void saveContent(String content) throws IOException {
    Writer writer = new BufferedWriter(new FileWriter(file));
    try {
      writer.write(content);
    } finally {
      writer.close();
    }
  }
}
