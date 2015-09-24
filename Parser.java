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
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = inputStream.read()) > 0) {
            output.append(data);
        }
        return output.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        StringBuilder outputBuilder = new StringBuilder();
        int data;
        while ((data = inputStream.read()) > 0) {
            if (data < 0x80) {
                outputBuilder.append(data);
            }
        }
        return outputBuilder.toString();
    }

    public synchronized void saveContent(String content) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i++) {
            outputStream.write(content.charAt(i));
        }
    }
}
