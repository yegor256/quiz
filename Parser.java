import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * This class is thread safe.
 */

public class Parser {

  public String getContent(File file) throws IOException {

    FileInputStream inputStream = new FileInputStream(file);
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder builder = new StringBuilder();
    String inputLine;

    while ((inputLine = reader.readLine()) != null)
      builder.append(inputLine);

    reader.close();

    return builder.toString();

  }

  public String getContentWithoutUnicode(File file) throws IOException {

    FileInputStream inputStream = new FileInputStream(file);
    String output = "";
    int data;

    while ((data = inputStream.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }

    inputStream.close();

    return output;

  }

  public void saveContent(String content, File outputFile) throws IOException {

    byte[] buffer = content.getBytes(); 

    FileOutputStream outputStream = new FileOutputStream(outputFile);
    outputStream.write(buffer);
    outputStream.close();

  }

}
