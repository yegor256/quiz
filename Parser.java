import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
	private static Parser instance = null;
	private File file;
	
	private static Object mutex= new Object();
    private Parser(){}
 
    public static Parser getInstance(){
        if(instance==null){
            synchronized (mutex){
                if(instance==null) instance= new Parser();
            }
        }
        return instance;
    }
	public synchronized void setFile(File f) {
		file = f;
	}
	public synchronized File getFile() {
		return file;
	}
	
	/**
	 * 
	 * @param includeUnicode, will be False if unicode not required
	 * @return
	 * @throws IOException
	 */
	public String getContent(boolean includeUnicode) throws IOException {
		FileInputStream i = null;
		String ContentWithoutUnicode = "";
		String ContentWithUnicode = "";
		try {
			i = new FileInputStream(file);
			int data;
			while ((data = i.read()) > 0) {
				if(!includeUnicode && data < 0x80){
					ContentWithoutUnicode += (char) data;
				} else {
					if (includeUnicode) ContentWithUnicode += (char) data;
				}

			}
		}
		finally {
			if(i != null) i.close();
		}
		return includeUnicode ? ContentWithUnicode : ContentWithoutUnicode ;
	}

	public void saveContent(String content) throws IOException {
		FileOutputStream o = null;
		try{
			o = new FileOutputStream(file);
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		} finally
		{
			if(o != null){
				o.close();
			}
		}
	}
}