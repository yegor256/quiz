import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public final class Parser {

  private File file;
  
  public Parser(File file) {
    this.file = file;
  }
  
  public Parser(String path) {
    this(new File(path));
  }
  
  public final synchronized String content() throws IOException {
    final FileInputStream fis = new FileInputStream(file);
    final Reader reader = new BufferedReader(new InputStreamReader(fis, "UTF8"));
    final StringBuffer output = new StringBuffer();
    int data;
    while ((data = reader.read()) > 0) {
      output.append((char)data);
    }
    reader.close();
    return new String(output);
  }
  
  public final synchronized String getContentWithoutUnicode() throws IOException {
    final FileInputStream fis = new FileInputStream(file);
    final StringBuffer output = new StringBuffer();
    int data;
    while ((data = fis.read()) > 0) {
      if (data < 0x80) {
        output.append((char) data);
      }
    }
    fis.close();
    return new String(output);
  }
  
  public final synchronized void write(String content) throws IOException {
    final FileOutputStream o = new FileOutputStream(file);
    final BufferedOutputStream bso = new BufferedOutputStream(o);
    for (int i = 0; i < content.length(); i += 1) {
      bso.write(content.charAt(i));
    }
    bso.close();
  }
  
  public static void main(String s[]) throws IOException {
    final Parser p = new Parser("profile.txt");
    System.out.println(p.content());
    //p.write(s[0]);
  }
}
