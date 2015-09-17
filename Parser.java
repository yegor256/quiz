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
        FileInputStream inputStream = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = inputStream.read()) > 0) {
            output += (char) data;
        }
        return output;
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = inputStream.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }

    public synchronized void saveContent(String content) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i++) {
            outputStream.write(content.charAt(i));
        }
    }
}
