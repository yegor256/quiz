import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    public synchronized void setFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return this.file;
    }

    public String getContent() throws IOException {
        FileInputStream input = new FileInputStream(file);
        String content = "";
        int data;
        while ((data = input.read()) > 0) {
            content += (char) data;
        }
        return content;
    }

    public String getContentWithoutUnicode() throws IOException {
        FileInputStream input = new FileInputStream(file);
        String content = "";
        int data;
        while ((data = input.read()) > 0) {
            if (data < 0x80) {
                content += (char) data;
            }
        }
        return content;
    }

    public void saveContent(String content) throws IOException {
        FileOutputStream output = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            output.write(content.charAt(i));
        }
    }
}
