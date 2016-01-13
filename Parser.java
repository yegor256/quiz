import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
 /**
  * The file to be parsed.
  */
  private final File file;
  
  /**
   * Constructor of Parser.
   * @param f the file to be parsed.
   */
  public Parser (File f){
        file=f;
  }
  /**
   * Returns the file that is being parsed.
   * @return the file that is being parsed.
   */
  public synchronized File getFile() {
    return file;
  }
  
  /**
   * Returns the content of the file.
   * @param withoutUnicode    indicates if the content returned should be with ore without unicode.
   * @return  returns the content.
   * @throws IOException  if there is a problem reading the file.
   */
  public synchronized String getContent(boolean withoutUnicode) throws IOException {
        FileInputStream i = new FileInputStream(file);
        StringBuilder output =new StringBuilder();
        int data;
        while ((data = i.read()) > 0) {
            if(withoutUnicode){
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
            else {
                output.append((char) data);
            }
        }
        return output.toString();
  }
  
  /**
     * Saves the content to the file.
     * @param content the content to be written to the file.
     * @throws IOException if there is a problem writting the file.
     */
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
