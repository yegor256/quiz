import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {

  public static final int DEFAULT_BUFFER_SIZE = 1024 * 16;

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
          if ((int) buffer[i] < 0x80) {
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
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    try {
      fileOutputStream.write(content.getBytes());
    } finally {
      fileOutputStream.close();
    }
  }
}
