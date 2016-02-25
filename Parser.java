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

    public String getContent() throws IOException {
        // In this case file can be null
        if (file == null) {
            throw new FileNotFoundException();
        }

        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            output += (char) data;
        }
        return output;
    }

    public String getContentWithoutUnicode() throws IOException {
        if (file == null) {
            throw new FileNotFoundException();
        }

        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }

    public void saveContent(String content) throws IOException {
        // in all cases file can be null
        if (file == null) {
            throw new FileNotFoundException();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            for (int i = 0; i < content.length(); i += 1) {
                fos.write(content.charAt(i));
            }
        } finally {
            // This is changed because from java standards output streams need to be closed
            if (fos != null) {
                fos.close();
            }
        }
    }
}
