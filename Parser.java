import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is thread safe.
 */
public class Parser {

    private static final Logger LOG = Logger.getLogger(Parser.class.getName());

    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() {

        String output = "";
        int data;

        try {
            FileInputStream i = new FileInputStream(file);
            while ((data = i.read()) > 0) {
                output += (char) data;
            }
        } catch (IOException iOException) {
            LOG.log(Level.SEVERE, "Cannot read file");
        }
        
        return output;
    }

    public String getContentWithoutUnicode() {

        String output = "";
        int data;
        
        try {
            FileInputStream i = new FileInputStream(file);
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
        } catch (IOException iOException) {
            LOG.log(Level.SEVERE, "Cannot read file");
        }
        
        return output;
    }

    public void saveContent(String content) throws IOException {
        FileOutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}

 
