import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
   
    //make file object immutable
    private final File file;
  
    public Parser(File file) {
		this.file = file;
   }
	
	/**
     * returns the file object
     * @return
     */
	public synchronized File getFile() {
		return file;
	}
  
    /**
     * returns the content of the file as a string
     * @return
     * @throws IOException
     */  
	public String getContent() throws IOException {
        return getContent(false);
    }

	/**
     * returns the content of the file without unicode range
     * @return
     * @throws IOException
     */
    public String getContentWithoutUnicode() throws IOException {
        return getContent(true);
    }

    private String getContent(boolean withoutUnitcode) throws IOException {

        StringBuilder output = new StringBuilder();
        //try with resources
        try(FileInputStream i = new FileInputStream(file)) {
            int data;
            while((data = i.read()) > 0){
                if (!withoutUnitcode || data < 0x80) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }

	 /**
     * saves the given string to the file
     * @param content
     * @throws IOException
     */
    public void saveContent(String content) throws IOException {

        try(FileOutputStream o = new FileOutputStream(file)) {
            o.write(content.getBytes());
        }
    }
}
