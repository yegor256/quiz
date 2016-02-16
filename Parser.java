
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
/**
 * This class is thread safe.
 */
public class Parser {
    File sdcard = Environment.getExternalStorageDirectory();
    private File file = new File(sdcard,"example.txt");
 
    public void setFile(File f) {
        file = f;
    }
 
    public File getFile() {
        return file;
    }
 
    public String getContent() {
 
        FileInputStream i = null;
        String output = "";
        try {
            i = new FileInputStream(file);
 
            int data;
            try {
                while ((data = i.read()) != -1) {
                    output += (char) data;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException fn) {
            fn.printStackTrace();
 
        } finally {
            try {
                if (i != null)
                    i.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return output;
    }
 
    public String getContentWithoutUnicode() {
 
        FileInputStream i = null;
        String output = "";
        try {
            i = new FileInputStream(file);
            int data;
            while ((data = i.read()) != -1) {
                if (data < 0x80) {
                    break;
                }
                output += (char) data;
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
        return output;
    }
 
    public void saveContent(String content) {
 
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(file);
            byte[] contentInBytes = content.getBytes();
            o.write(contentInBytes);
            o.flush();
            o.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
 
            try {
                if (o != null) {
                    o.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
 
            }
        }
    }
}
