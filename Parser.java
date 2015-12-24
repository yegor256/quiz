import java.io.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuffer;

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

  public synchronized String getContent() throws IOException {
    BufferedReader reader = new BufferedReader(new java.io.FileReader(file));

    StringBuffer output = new StringBuffer();
    while (String line = reader.readLine()) {
      output.append(line);
    }

    return output.toString();
  }

  public synchronized String getContentWithoutUnicode() throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(file, "US-ASCII"));

    StringBuffer output = new StringBuffer();
    while (String line = reader.readLine()) {
      output.append(line);
    }

    return output.toString();
  }
  
  public synchronized void saveContent(String content) throws IOException {
    BufferedWriter o = new BufferedWriter(new FileOutputStream(file));
    o.write(content.);
  }
}
