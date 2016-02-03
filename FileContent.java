import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class FileContent {

    private static File file;

    public static synchronized void setFile(File f) {
        file = f;
    }
    public static synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        return getContent(true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(false);
    }

    public String getContent(boolean hasUnicode) throws IOException {
        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;

        while ((data = i.read()) > 0) {
            if (!hasUnicode && data < 0x80) {
                output += (char) data;
            } else {
                output += (char) data;
            }
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