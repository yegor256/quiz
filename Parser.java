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
        ensureFile();
        FileInputStream inputStream = new FileInputStream(file);
        String output = getContent(inputStream);
        inputStream.close();
        return output;
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        ensureFile();
        FileInputStream inputStream = new FileInputStream(file);
        String output = getContentWithoutUnicode(inputStream);
        inputStream.close();
        return output;
    }

    private void isUnicode(int data) {
        return data >= 0x80;
    }

    private void ensureFile() {
        if (file == null) {
            throw new RuntimeException("file is not set. Call setFile before getting or saving content.");
        }
    }

    private String getContentWithoutUnicode(FileInputStream stream){
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (!isUnicode(data)) {
                output += (char) data;
            }
        }
        return output;
    }

    private String getContent(FileInputStream stream){
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            output += (char) data;
        }
        return output;
    }

    public synchronized void saveContent(String content) throws IOException {
        ensureFile();

        FileOutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}
