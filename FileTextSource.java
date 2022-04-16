import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public final class FileTextSource implements TextSource {
  private final File file;

  public FileTextSource(final File source) {
    this.file = source;
  }

  public FileTextSource(final String path) {
    this(new File(path));
  }

  public String getContent() throws IOException {
    final FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    return output;
  }

  @Deprecated
  public synchronized void setFile(File f) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  public synchronized File getFile() {
    throw new UnsupportedOperationException();
  }
}
