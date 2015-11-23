import java.io.*;
import java.lang.RuntimeException;

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

  public String getContent() {
    return prepareContent(false);
  }

  public String getContentWithoutUnicode() {
    return prepareContent(true);
  }

  /**
   * Read the content of file and returns String
   * throws exceptioin if fails to read
   * @param withoutUnicode contain unicode or not
   * @return String contain data of file
   * @throws CustomException
   */
  private String prepareContent(boolean withoutUnicode) {
    BufferedInputStream bufferedInputStream = null;
    StringBuilder output = new StringBuilder("");
    try {
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(getFile()));
        } catch (FileNotFoundException e){
            //log exception stacktrace here
            throw new CustomException("File Not found : "+e.getStackTrace());
        }
      int data;
      while ((data = bufferedInputStream.read()) > 0) {
        if (withoutUnicode && !(data < 0x80)) continue;
        output.append((char) data);
        bufferedInputStream.close();
      }
    } catch (IOException e){
      //log exception stacktrace here
      throw new CustomException("Not able to read the content of file : "+e.getStackTrace());
    }

    return output.toString();
  }

  /**
   * Takes content from a String and transformed the String
   * to file content.
   * @param content contains the data for saving to file
   * @throws CustomException
   */
  public void saveContent(String content)  {
    try {
        BufferedOutputStream bufferedOutputStream = null;
        try { 
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(getFile()));
        } catch (FileNotFoundException e){
            //log exception stacktrace here
            throw new CustomException("File Not found : "+e.getStackTrace());
        }
      for (int i = 0; i < content.length(); i += 1) {
        bufferedOutputStream.write(content.charAt(i));
      }
      bufferedOutputStream.close();
    } catch (IOException e){
      //log exception stacktrace here
      throw new CustomException("Not able to save the content to file : "+e.getStackTrace());
    }
  }
}

/**
 * @author bilal
 * @since 23-Nov-2015
 *
 * CustomException to be use so that client
 * do not have to handle different exceptions
 */
class CustomException extends RuntimeException {

  public CustomException() {
  }

  public CustomException(String s) {
    super(s);
  }
}
