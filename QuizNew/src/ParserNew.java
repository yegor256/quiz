import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * 1. Check the IO Exceptions with try/catch when read/write file
 * 2. Close the stream with finally for read/write operations
 * 3. Use StringBuilder for string concatenation which is faster.
 * 4. Check if the file exists before writing to file and create it if it is null
 * 5. Convert string content to byte so that it could write into the stream.
 * 6. Flushes the output stream and forces any buffered output bytes to be written out.This will improve the IO performance. 
 *
 * 
 * 
 * @author marcelanita
 *
 */
public class ParserNew {
	 private File file;
	  public synchronized void setFile(File f) {
	    file = f;
	  }
	  public synchronized File getFile() {
	    return file;
	  }
	  public String getContent() throws IOException {
			FileInputStream i = null;
			StringBuilder output = new StringBuilder();
			try {
				i = new FileInputStream(file);

				System.out.println("Total file size to read (in bytes) : "
						+ i.available());

				int data;
				while ((data = i.read()) != -1) {
					// convert to char 
					  output.append((char) data);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (i != null)
						i.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			return output.toString();
	  }
	  public String getContentWithoutUnicode() throws IOException {
		  FileInputStream i = null;

		  StringBuilder output = new StringBuilder();
		  int data;
		  try{
			  i = new FileInputStream(file);
			  while ((data = i.read()) > 0) {
				  if (data < 0x80) {
					  output.append((char) data);
				  }
			  }} catch (IOException e) {
				  e.printStackTrace();
			  } finally {
				  try {
					  if (i != null)
						  i.close();
				  } catch (IOException ex) {
					  ex.printStackTrace();
				  }
			  }
		  return output.toString();
	  }
	  public void saveContent(String content) throws IOException {
		  FileOutputStream o = null;
		  try{ 
			  o = new FileOutputStream(file);
			// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = content.getBytes();

				o.write(contentInBytes);
				o.flush();
				o.close();
		  } catch (IOException e) {
			  e.printStackTrace();
		  } finally {
			  try {
				  if (o!= null)
					  o.close();
			  } catch (IOException ex) {
				  ex.printStackTrace();
			  }
		  }
	  }
	 
}
