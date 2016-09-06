import com.sun.istack.internal.NotNull;

import java.io.*;

/**
 * A thread-safe helper class for reading a files content as text,
 * or writing text into it.
 */
public class FileReadWriteHelper {

  public static final int DEFAULT_BUFFER_SIZE = 1024 * 16;
  public static final int ASCII_LOWER_BOUND = 0x2F;
  public static final int ASCII_UPPER_BOUND = 0x80;

  private final File file;

  public FileReadWriteHelper(File file) {
    this.file = file;
  }


  public synchronized String getContent() throws IOException {
    return getContent(false);
  }

  public synchronized String getContentWithoutUnicode() throws IOException {
    return getContent(true);
  }

  protected String getContent(boolean onlyAscii) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(file);
    StringBuilder result = new StringBuilder();
    try {
      Reader reader = new BufferedReader(new InputStreamReader(fileInputStream));
      char[] buffer = new char[DEFAULT_BUFFER_SIZE];
      int read;
      if (onlyAscii) {
        while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
          for (int i = 0; i < read; i++) {
            int charCode = (int) buffer[i];
            if (charCode > ASCII_LOWER_BOUND && charCode < ASCII_UPPER_BOUND) { // char code between 31 and 128
              result.append(buffer[i]);
            }
          }
        }

      } else {
        while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
          result.append(buffer, 0, read);
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
