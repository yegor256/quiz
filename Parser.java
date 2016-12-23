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

	public  String getContent() throws IOException {

		synchronized (file) {
			FileInputStream input = new FileInputStream(file);
			String output = "";
			try {

				int data;
				while ((data = input.read()) > 0) {
					output += (char) data;
				}
			} catch (IOException e) {
				throw e;
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (input != null) {
					input.close();
				}
			}
			return output;
		}
		

	}

	public  String getContentWithoutUnicode() throws IOException {

		synchronized (file) {
			FileInputStream input = new FileInputStream(file);
			String output = "";
			try {
				
				int data;
				while ((data = input.read()) > 0) {
					if (data < 0x80) {
						output += (char) data;
					}
				}
			} catch (IOException e) {
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if( input != null){
					input.close();
				}
			}

			return output;
		}
		
	}

	public  void saveContent(String content) throws IOException {
		synchronized (file) {
			FileOutputStream output = new FileOutputStream(file);
			try {
			
				output.write(content.getBytes());
				
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if( output != null){
					output.close();
				}
			}
		}
		
	}
}
