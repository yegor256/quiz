import java.lang.StringBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class FileAccessor {

  private File file;

  public synchronized void setFile(File f) {
    file = f;
  }

  public synchronized File getFile() {
    return file;
  }

  /**
   * @param asciiOnly if true, will skip reading any non ascii characters (reads only from an inclusive range of 0x00 to 0x79)
   *
   */
  public String getContent(boolean asciiOnly) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(getFile()));
    StringBuilder output = new StringBuilder();

    try {
      String line = br.readLine();
      for (;line != null; line = br.readLine()) {
        if (asciiOnly) {
          line = stripNonAsciiCharacters(line);
        }

        output.append(line);
        output.append(System.getProperty("line.separator"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
        br.close();
    }

    return output.toString();
  }

  /**
   * Use this method to write out data that is too large to fit into a single string.
   *
   * @param is an inputstream pointing to the data to be written
   */
  public void saveContents(InputStream is) throws IOException {
    FileOutputStream fos = new FileOutputStream(getFile());

    try {
      for (int b = is.read(); b != -1; b = is.read()) {
        fos.write(b);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      fos.flush();
      fos.close();
    }
  }

  /**
   * Convenience method to write data if it already exists as a string.
   *
   * @param content the string to write to the file
   */
  public void saveContent(String content) throws IOException { 
    FileWriter fw = new FileWriter(file);
    try {
      fw.write(content);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      fw.flush();
      fw.close();
    }
  }

  private String stripNonAsciiCharacters(String input) {
    String result = input.replaceAll("[^\\x00-\\x7E]", "");
    return result;
  }


  //TODO: delete, just for testing
  public static void main(String[] args) throws Exception {
    FileAccessor fa = new FileAccessor();
    File f = new File("testFile.txt");
    fa.setFile(f);

    String content = fa.getContent(true);
    System.out.println(content);


    FileAccessor fa2 = new FileAccessor();
    File f2 = new File("out.txt");
    fa2.setFile(f2);
    fa2.saveContent("abcd");
  }
}
