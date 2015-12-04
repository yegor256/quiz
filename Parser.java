import java.io.ByteArrayOutputStream;
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

	public synchronized String getContent() throws IOException {
		
		try(
				FileInputStream i = new FileInputStream(file);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
			) {
				byte[] b = new byte[2048];
				int len = 0;
				while ((len = i.read(b, 0, 2048)) != -1) {
					out.write(b, 0, len);
				}
				return out.toString();
			} catch(IOException e) {
				throw e;
			}
	}
	
	public synchronized String getContentWithoutUnicode() throws IOException {
		
		try(
			FileInputStream i = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		) {

			byte[] b = new byte[2048];
			int len = 0;
			while ((len = i.read(b, 0, 2048)) != -1) {
				filterUnicode(out, b, len);
			}
			return out.toString();
						
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void filterUnicode(ByteArrayOutputStream out, byte[] data, int len) {
		for(int i=0; i<len; i++) {
			if(data[i] < 0x80) {
				out.write(data[i]);
			}
		}
	}

	public synchronized void saveContent(String content) throws IOException {
		try( 
			FileOutputStream o = new FileOutputStream(file);
		) {
			o.write(content.getBytes());
		} catch (Exception e) {
			throw e;
		}
	}
}