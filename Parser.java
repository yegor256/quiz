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
  public String getContent(DataAdderStrategy adder) throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      adder.addData(output,data);
    }
    return output;
  }

  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }


  private interface DataAdderStrategy {
    public void addData(String output, int data);
  }

  public static class DefaultDataAdder implements DataAdderStrategy{
    public void addData(String output, int data){
      output += (char) data;
    }
  }
  public static class EncodingDataAdder implements DataAdderStrategy{
    public void addData(String output, int data){
      if (data < 0x80) {
        output += (char) data;
      }
    }
  }
}
}