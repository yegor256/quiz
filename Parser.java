import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File mFile;
  
  public synchronized void setFile(File f) {
    mFile = f;
  }
  
  public synchronized File getFile() {
    return mFile;
  }
  
  public String getContent() throws IOException {
    FileInputStream inStream = new FileInputStream( mFile );
    StringBuilder output = new StringBuilder();
    int readChar = 0;
    
    while (( readChar = inStream.read() ) > 0 ) {
      output.append( (char) readChar );
    }
    
    return output.toString();
  }
  
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream inStream = new FileInputStream( mFile );
    StringBuilder output = new StringBuilder();
    int readChar = 0;
    
    while (( readChar = i.read() ) > 0 ) {
      if ( readChar < 0x80 ) {
        output.append( (char) readChar );
      }
    }
    
    return output.toString();
  }
  
  public void saveContent( String content ) throws IOException {
    FileOutputStream outStream = new FileOutputStream( mFile );
    
    outStream.write( content.getBytes() );
    outStream.close();
  }
}
