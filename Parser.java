import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    public Parser() {
        this.file = null;
    }

    public synchronized void setFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return this.file;
    }

    public String getContent() throws IOException {
        return this.getContentHelper(true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return this.getContentHelper(false);
    }

    public void saveContent(String content) throws IOException {
        FileOutputStream output = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            output.write(content.charAt(i));
        }
    }

    private String getContentHelper(boolean withUnicode) {
        FileInputStream input = new FileInputStream(file);
        String content = "";
        int data;

        if (withUnicode == false) {
            while ((data = input.read()) > 0) {
                if (data < 0x80) {
                    content += (char) data;
                }
            }
        }
        else {
            while ((data = input.read()) > 0) {
                content += (char) data;
            }
        }
    }
}
