import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
  public String getContent(String encoding) throws IOException {
		String output = "";
		if (file.length() > 0) {
			byte[] by = Files.readAllBytes(file.toPath());
			output = new String(by, encoding);
		}
		return output;
	}

	public String getContentWithoutUnicode() throws IOException {
		String output = "";
		output = getContent("UTF-8");
		return output;
	}

	public void saveContent(String content) throws IOException {
		FileWriter fw = new FileWriter(file);
		fw.write(content);
		fw.flush();
		fw.close();
	}
}
