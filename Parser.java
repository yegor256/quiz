import java.io.*;

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

  public String getContent() throws IOException {
    return getContentInternal(new AlwaysTruePredicate<Integer>());
  }

  public String getContentWithoutUnicode() throws IOException {
    return getContentInternal(new UnicodePredicate());
  }

  private synchronized String getContentInternal(Predicate<Integer> predicate) throws IOException {
    File currentFile = file;
    if(!currentFile.exists()){
      //FIXME: clarify requirements for non existing file
      return "";
    }
    FileInputStream i = new FileInputStream(currentFile);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (predicate.apply(data)) {
        output += (char) data;
      }
    }
    return output;
  }

  public synchronized void saveContent(String content) throws IOException {
    File currentFile = file;
    FileOutputStream o = new FileOutputStream(currentFile);
    try(Writer fileWriter = new BufferedWriter(new OutputStreamWriter(o))){
      fileWriter.write(content);
    }
  }

  private interface Predicate<TYPE> {
    boolean apply(TYPE argument);
  }

  private static class AlwaysTruePredicate<TYPE> implements Parser.Predicate<TYPE> {
    @Override
    public boolean apply(TYPE argument) {
      return true;
    }
  }

  private static class UnicodePredicate implements Parser.Predicate<Integer> {
    @Override
    public boolean apply(Integer argument) {
      return argument < 0x80;
    }
  }

}
