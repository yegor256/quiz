import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
					// convert to char and display it
					System.out.print((char) data);
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
			  for (int i = 0; i < content.length(); i += 1) {
				  o.write(content.charAt(i));
			  }
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
	  public static void main(String[] args){
		  ParserNew parser = new ParserNew();
		  parser.setFile(new File(""));
	  }
}
