import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
import java.nio.channels.FileChannel;

/**
 * This class is thread safe.
 */
public class Parser {

  /*
   * Using volatile implies cpu-level memory barriers. This ensures
   * that all _active_ calls to read/write methods (i.e. getContent)
   * operate on the file that was previously set, while all _future_
   * calls to the methods operate on the new file.
   */
  private volatile File file;

  public Parser() {
  }

  public void setFile(File f) {
    this.file = f;
  }

  public File getFile() {
    return this.file;
  }

  public String getContent() throws IOException {
    return new String(Files.readAllBytes(file.toPath()));
  }

  public String getContentWithoutUnicode() throws IOException {
    /*
     * Depending on how large the file is, mapping it
     * might not be plausible (however, since we're stuffing
     * a the read data into a string builder, I don't imagine
     * it'd be that big anyway.)
     */
    MappedByteBuffer mmap;
    StringBuilder res = new StringBuilder();

    try (FileChannel fc = FileChannel.open(file.toPath(),
                                           StandardOpenOption.READ)) {
      mmap = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
      while (mmap.hasRemaining()) {
      	int c = mmap.get() & 0xff;
      	if (c < 0x80)
      	  res.append((char) c);
      }
    }
    return res.toString();
  }

  public void saveContent(String content) throws IOException {
    Files.write(file.toPath(), content.getBytes());
  }
}
