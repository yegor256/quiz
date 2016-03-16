import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {
  private File file;

  public Parser(File file) {
    this.file = file;
  }

  public synchronized File getFile() {
    return file;
  }

  private FileOutputStream getFileOutputStream() throws IOException {
    synchronized (this) {
      checkFileType();
      return new FileOutputStream(getFile());
    }
  }

  private FileReader getFileReader() throws IOException {
    synchronized (this) {
      checkFileType();
      return new FileReader(getFile());
    }
  }

  private void checkFileType() throws IOException {
    if (!file.isFile()) {
      throw new IOException("File [" + file.getAbsolutePath() + "] not valid");
    }
  }

  public String getContent() throws IOException {
    FileReader fileReader = getFileReader();
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    StringBuilder contentBuilder = new StringBuilder();
    String output = "";
    try {
      boolean isNotFirstLine = false;
      while ((output = bufferedReader.readLine()) != null) {
        if (isNotFirstLine) {
          contentBuilder.append("\n");
        }
        contentBuilder.append(output);
        isNotFirstLine = true;
      }
    } catch (IOException ioException) {
      throw ioException;
    } finally {
      bufferedReader.close();
      fileReader.close();
    }
    return contentBuilder.toString();
  }

  public String getContentWithoutUnicode() throws IOException {
    FileReader fileReader = getFileReader();
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    StringBuilder contentBuilder = new StringBuilder();
    int data;
    try {
      while ((data = bufferedReader.read()) > 0) {
        if (data < 0x80) {
          contentBuilder.append((char) data);
        }
      }
    } catch (IOException ioException) {
      throw ioException;
    } finally {
      bufferedReader.close();
      fileReader.close();
    }

    return contentBuilder.toString();
  }

  public void saveContent(String content) throws IOException {
    FileOutputStream fileOutputStream = getFileOutputStream();
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);

    try {
      bufferedOutputStream.write(content.getBytes());
    } catch (IOException ioException) {
      throw ioException;
    } finally {
      bufferedOutputStream.close();
      fileOutputStream.close();
    }
  }

}
