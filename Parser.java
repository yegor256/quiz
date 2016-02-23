import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    return loadContent(false);
  }
  public String getContentWithoutUnicode() throws IOException {
    return loadContent(true);
  }
  private String loadContent(final boolean withoutUnicode) throws IOException {
    StringBuilder stringBuilder = new StringBuilder("");
    try(FileInputStream i = new FileInputStream(file)){
        int data;
        while ((data = i.read()) > 0) {
            if (!withoutUnicode || (data < 0x80)) {
                stringBuilder.append((char) data);
            }
        }
    }
    return stringBuilder.toString();
  }
  public void saveContent(String content) throws IOException {
    try(FileOutputStream o = new FileOutputStream(file)){
        for (int i = 0; i < content.length(); i++) {
            o.write(content.charAt(i));
        }
    }
  }
}
