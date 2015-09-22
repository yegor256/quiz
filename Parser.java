import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	public String getContent() {
		if(file==null){
			return null;
		}
		FileInputStream i = null;
		StringBuffer sb = new StringBuffer();
		try {
			i = new FileInputStream(file);
			int data;
			while ((data = i.read()) > 0) {
				sb.append((char) data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (i != null) {
				try {
					i.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

	public String getContentWithoutUnicode() {
		if(file==null){
			return null;
		}
		FileInputStream i = null;
		StringBuffer sb = new StringBuffer();
		try {
			i = new FileInputStream(file);

			int data;
			while ((data = i.read()) > 0) {
				if (data < 0x80) {
					sb.append((char) data);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (i != null) {
				try {
					i.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

	public void saveContent(String content) {
		if(file==null){
			return;
		}
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (o != null) {
				try {
					o.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}