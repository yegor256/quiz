import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * This class is thread safe.
 */
// TODO: split File setter/getter and read/write synchronization
public class Parser {
  private static final int BUFFSIZE = 1024*16;
private File file;
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent() throws IOException {
	return getContentInternal(true);
  }
  public String getContentWithoutUnicode() throws IOException {
	return getContentInternal(false);
  }
  
  synchronized private String getContentInternal(boolean all) throws IOException {
    InputStream is = new BufferedInputStream(new FileInputStream(file));
    StringBuilder sb = new StringBuilder();
    byte buff[] = new byte[BUFFSIZE];
    int rc;
    while( (rc=is.read(buff, 0, BUFFSIZE)) != -1 ){
    	for(int i=0;  i<rc;  ++i ){
    	      if( all || buff[i] < 0x80 ) {
    	    	  sb.append((char) buff[i]);
    	      }
    	}
    }
    is.close();
    return sb.toString();
  }
  
  synchronized public void saveContent(String content) throws IOException {
    OutputStream o = new BufferedOutputStream(new FileOutputStream(file));
    o.write(content.getBytes());
    o.close();
  }
}

