
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

    private File file = null;

    /**
     *
     * @param f
     */
    public void setFile(File f) {
        file = f;
    }

    /**
     *
     * @param title
     */
    public void setFile(String title) {
        file = new File(title);
    }

    /**
     *
     * @return
     */
    public File getFile() {
        return file;
    }

    /**
     *
     * @param withUnicode
     * @return
     */
    public synchronized String getContent(boolean withUnicode) {
        FileInputStream i = null;
        String output = "";
        try {
            i = new FileInputStream(file);

            int data;
            while ((data = i.read()) > 0) {
                output += (char) data;
            }
            if (withUnicode && (data < 0x80)) {
                output += (char) data;
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                i.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return output;
    }

    public synchronized void saveContent(String content) {
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(file);
            for (char c : content.toCharArray()) {
                o.write(c);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
