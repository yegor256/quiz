import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {

  //Yury_Lauresh

  // About this methods
  // It is not thread safe because we should write and read in one time
  // in getContent and getContentWithoutUnicode we can work with buffer and with StringBuilder it will be better.
  // We must close streams.
  // I am not sore about skipping unicode symbols, but i have just 15 minutes )

  //  I think that it is very bad API.
  // - Name parser. Parser should do just parse() not open file and so on. For this reason I propose to add class something like FileHandler.
  // And work with stream in parser, not with file.

  private File file;

  public synchronized void setFile(File f) {
    file = f;
  }

  public synchronized File getFile() {
    return file;
  }

  public synchronized String getContent() throws IOException {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
      String strLine;
      while ((strLine = br.readLine()) != null) {
        sb.append(strLine);
      }
    }
    return sb.toString();
  }

  public synchronized String getContentWithoutUnicode() throws IOException {
    StringBuilder sb = new StringBuilder();
    try(FileInputStream i = new FileInputStream(file)){
      int data;
      while ((data = i.read()) >= 0) {
        if (data < 0x80) {
          sb.append(data);
        }
      }
    }
    return sb.toString();
  }

  public synchronized void saveContent(String content) throws IOException {
    try(FileOutputStream o = new FileOutputStream(file)){
      for (int i = 0; i < content.length(); i += 1) {
        o.write(content.charAt(i));
      }
      o.flush();
    }
  }
}
