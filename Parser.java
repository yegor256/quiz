import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  FileInputStream i;
  FileOutputStream o;
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent(){
    String output = "";
    try{
    i = new FileInputStream(file);
    
    int data;
    //Should use bufferedInputstream instead of below code
    while ((data = i.read()) > 0) {
      output += (char) data;
    }
    }catch(IOException e){}
    finally{
      i.close();
    }
    return output;
  }
  public String getContentWithoutUnicode() {
    String output = "";
    try{
    i = new FileInputStream(file);
    
    int data;
    //Should use bufferedInputstream instead of below code
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }}catch(IOException e){
      //catch Exception
    }
    finally{
      i.close();
    }
    return output;
  }
  public void saveContent(String content) {
    try{
     o = new FileOutputStream(file);
     //Should use bufferedoutstream instead of below code
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
    }catch(IOException e){
      //catch Exception
    }
    finally{
      o.close();
    }
  }
}
