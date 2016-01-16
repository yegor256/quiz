import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  
  private static final int BUF_SIZE = 1024;
  
  private File file;
  
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  
  public synchronized String getContent() throws IOException {
    
      StringBuilder content = new StringBuilder();
      BufferedReader reader = new BufferedReader(new FileReader(file));
      char[] buf = new char[BUF_SIZE];
      int num = 0;

      try {
          while ((num = reader.read(buf, 0, BUF_SIZE)) != -1) {
              content.append(buf, 0, num);
          }
      } finally {
          reader.close();
      }

      return content.toString();
  }
  
  public String getContentWithoutUnicode() throws IOException {
    
    StringBuilder content = new StringBuilder();
    BufferedInputStream input =
            new BufferedInputStream(new FileInputStream(file));
    int data;
    try {
        while ((data = input.read()) != -1) {
            if (data < 0x80) {
                content.append((char) data);
            }
        }
    } finally {
        input.close();
    }

    return content.toString();
    
  }
  public void saveContent(String content) throws IOException {
    
    FileWriter writer = new FileWriter(file);
    try {
        writer.write(content);
    } finally {
        writer.close();
    }
  }
}
