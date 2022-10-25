import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {

  private File file;

  public synchronized void setFile(File file) {
    this.file = file;
  }

  public synchronized File getFile() {
    return file;
  }

  public String getContent() throws IOException {

    FileInputStream fileInputStream = new FileInputStream(file);

    StringBuilder output = new StringBuilder();

    int amountDataToRead = fileInputStream.read();

    while (amountDataToRead > 0) {
      char fileData = (char) amountDataToRead;
      output.append(fileData);
    }

    fileInputStream.close();
    return output.toString();
  }

  public String getContentWithoutUnicode() throws IOException {

    FileInputStream fileInputStream = new FileInputStream(file);

    StringBuilder resultString = new StringBuilder();

    int amountDataToRead = fileInputStream.read();

    while (amountDataToRead > 0) {
      if (amountDataToRead < 0x80) {
        char fileData = (char)amountDataToRead;
        resultString.append(fileData);
      }
    }

    fileInputStream.close();

    return resultString.toString();
  }

  public void saveContent(String content) throws FileNotFoundException {

    try(FileOutputStream fileInputStream = new FileOutputStream(file)) {
      for (int i = 0; i < content.length(); i++) {
        fileInputStream.write(content.charAt(i));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
