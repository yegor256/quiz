import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is thread safe in the meaning that the data stored in it is thread-safe.
 * However, problems can occur if multiple threads start to read and write the file
 *   simultaneously (calling both getContent*() and saveContent()). Safety for such
 *   scenarios is a matter of further refactoring.
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
    BufferedReader br = new BufferedReader( new FileReader( getFile() ) );
    try {
      StringBuilder output = new StringBuilder();
      while(true) {
        int data = br.read();
        if( data < 0 ) {
          break;
        }
        output.append( (char)data );
      }
      return output.toString();
    } finally {
      br.close();
    }
  }
  
  public String getContentWithoutUnicode() throws IOException {
    String content = getContent();
    StringBuilder output = new StringBuilder();
    for( int i=0; i<content.length(); i++ ) {
      char ch = content.charAt(i);
      if( (int)ch < 0x80 ) {
        output.append(ch);
      }
    }
    return output.toString();
  }
  
  public void saveContent(String content) throws IOException {
    BufferedWriter bw = new BufferedWriter( new FileWriter( getFile() ));
    try {
      bw.write(content);
    } finally {
      bw.close();
    }
  }
  
}
