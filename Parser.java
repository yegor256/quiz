import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

    private File file;

    public Parser(File file) {
        this.file = file;
    }

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        return readFileInputStream(true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return readFileInputStream(false);
    }

    public synchronized void saveContent(String content) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            fileOutputStream.write(content.charAt(i));
        }
        fileOutputStream.close();
    }

    private synchronized String readFileInputStream(boolean unicode) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        StringBuffer output = new StringBuffer();
        int data;
        while ((data = fileInputStream.read()) > 0) {
            if (unicode) {
                output.append(data);
            } else if (data < 0x80) {
                output.append(data);
            }
        }
        fileInputStream.close();
        return output.toString();
    }
}
