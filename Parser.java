import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Parser {

  private final  File file;

  public Parser(File file) {
    this.file = file;
  }

  public String getContent() throws IOException {
    FileInputStream inputStream = null;
    try {
      inputStream = new FileInputStream(file);
      StringBuilder builder = new StringBuilder();
      int data;
      while ((data = inputStream.read()) > 0) {
        builder.append((char) data);
      }
      return builder.toString();
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }
  }

  public String getContentWithoutUnicode() throws IOException {
    FileInputStream inputStream = null;
    try {
      inputStream = new FileInputStream(file);
      StringBuilder builder = new StringBuilder();
      int data;
      while ((data = inputStream.read()) > 0) {
        if (data < 0x80) {
          builder.append((char) data);
        }
      }
      return builder.toString();
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }
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

  public File getFile() {
    return file;
  }
}
