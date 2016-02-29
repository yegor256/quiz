import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
  private final int EOF = -1;
  private File file;

  public String getContent() {
    String output = "";
    FileInputStream inputStream = null;
    try {
      inputStream= new FileInputStream(getFile());

      int data;
      while ((data = inputStream.read()) != EOF) {
        output += (char) data;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return output;
  }

  public String getContentWithoutUnicode(){
    String output = "";
    FileInputStream inputStream = null;
    try {
      inputStream = new FileInputStream(getFile());
      int data;
      while ((data = inputStream.read()) != EOF) {
        if (data < 0x80) {
          output += (char) data;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if(inputStream != null){
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return output;
  }

  public void saveContent(String content) {
    FileOutputStream outputStream = null;
    try {
      outputStream = new FileOutputStream(getFile());
      for (int i = 0; i < content.length(); i++) {
        outputStream.write(content.charAt(i));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      if(outputStream != null){
        try{
          outputStream.close();
        }catch (IOException e){
          e.printStackTrace();
        }
      }
    }
  }

  public synchronized File getFile() {
    return file;
  }

  public synchronized void setFile(File f) {
    file = f;
  }
}

