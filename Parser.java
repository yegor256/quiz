import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  public static final int CHAR_CUTOFF_TRESHOLD = 0x80;
  private File file;

  public synchronized void setFile(File f) {
    file = f;
  }

  public synchronized File getFile() {
    return file;
  }

  public String getContent() throws IOException {
    return readFile(false);
  }

  public String getContentWithoutUnicode() throws IOException {
    return readFile(true);
  }

  public String readFile(boolean applyFilter) throws IOException{
    if(file == null){
      return "";
    }
    StringBuilder output = new StringBuilder();

    try(FileInputStream i = new FileInputStream(file)){
      int data;

      while ((data = i.read()) > 0) {
        if (!applyFilter){
          output.append((char) data);
        }
        else if (data < CHAR_CUTOFF_TRESHOLD) {
          output.append((char) data);
        }
      }
    }

    return output.toString();
  }

  public void saveContent(String content) throws IOException {
    if(file == null){
      return;
    }

    try (FileOutputStream o = new FileOutputStream(file)) {
      for (int i = 0; i < content.length(); i += 1) {
          o.write(content.charAt(i));
      }
    }
  }
}
