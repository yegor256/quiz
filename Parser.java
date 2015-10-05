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
    FileInputStream input = new FileInputStream(file); // i is a poor variable name
    String content = "";

    int bytes = input.available(), // how much should we read?
      byte[] bArray = new byte[bytes]; // where are we going to put it?

    if( fis.read(bArray, 0, bytes) > 0) // read the file in one shot (faster than byte-wise but more memory intensive, would extend this to work in chunks of 512KB)
      content = new String(bArray);     // If we get positive bytes (success) then convert byte array to string

    input.close();

    return content;
  }

  public String getContentWithoutUnicode() throws IOException {
    FileInputStream fis = new FileInputStream(file);
    String content = "";

    int bytes = input.available(),
      byte[] bArray = new byte[bytes];

    if( fis.read(bArray, 0, bytes) > 0){  // read the file in one shot
      List bList = new List<Byte>(Arrays.asList(bArray));     // after succesful read convert to list for easy traversal
      for(Iterator<Byte> iter = bList.listIterator(); iter.hasNext();){ // iterator over bytes and remove non-ascii chars
        if( iter.next().intValue > (int) 0x80)
          iter.remove();
      }

      bArray = bList.stream().toArray(byte[]::new); // convert back to byte array, then to string. Cleaner than a for loop.
      content = new String(bArray);
    }

    fis.close();
    return content;
  }

  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
