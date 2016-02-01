import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  public synchronized void updateFile(File f) {
    if(f != null) {
		file = f;
	}
  }
  public synchronized File readFile() {
    return file;
  }
  
  public String readContent() throws IOException {
   FileInputStream fileInputStream = new FileInputStream(readFile());
   InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
   BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
   try {
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			builder.append(line);
		}
		return builder.toString();
    }catch(IOException e) {
		throw e;
    }finally {
     bufferedReader.close();
	 inputStreamReader.close();
	 fileInputStream.close();
   }
   return "";
 }
  
  public String readContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(readFile());
    try {
		StringBuilder builder = new StringBuilder();
        int data;
        while ((data = i.read()) > 0) {
			if (data < 0x80) {
				builder.append((char) data);
            }
        }
        return builder.toString();
	} catch(IOException e) {
		throw e;
    }finally {
           i.close();
    }
    return "";
  }
  public void updateContent(String content) throws IOException {
	if(content == null)
		return;
    FileOutputStream o = new FileOutputStream(readFile());
	try {
		o.write(content.getBytes());		
	} catch(IOException e){
		throw e;
	} finally {
		o.flush();
		o.close();
	}
}
