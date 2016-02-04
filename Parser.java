import java.io.*;


public class Parser {

  private final  File file;

  public Parser(File file) {
    this.file = file;
  }

  public File getFile() {
    return file;
  }

  public String getContent() throws IOException {
    FileInputStream fileStream = new FileInputStream(file);
    return readAndCloseStream(fileStream);
  }

  public String getContentWithoutUnicode() throws IOException {
    InputStream fileStream = new FileInputStream(file);
    InputStream wipeUnicodeStream = new WipeUnicodeInputStream(fileStream);
    return readAndCloseStream(wipeUnicodeStream);
  }

  public void saveContent(String content) throws IOException {
    FileOutputStream outputStream = null;
    try {
      outputStream = new FileOutputStream(file);
      outputStream.write(content.getBytes());
    } finally {
      if (outputStream != null) {
        outputStream.close();
      }
    }
  }

  private String readAndCloseStream(InputStream stream) throws IOException {
    try {
      StringBuilder builder = new StringBuilder();
      int data;
      while ((data = stream.read()) > 0) {
        builder.append((char) data);
      }
      return builder.toString();
    } finally {
      stream.close();
    }
  }


  /**
   *  Input Stream skipping all non-unicode characters
   */
  private static class WipeUnicodeInputStream extends InputStream {
    public static final int LAST_NONUNICODE = 0x80;
    private final InputStream childStream;

    private WipeUnicodeInputStream(InputStream childStream) {
      this.childStream = childStream;
    }

    @Override
    public int read() throws IOException {
      int value;
      do {
        value = childStream.read();
      } while (value > LAST_NONUNICODE);
      return value;
    }

    @Override
    public void close() throws IOException {
      childStream.close();
    }
  }
}
