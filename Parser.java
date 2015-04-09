import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
    private static final int UNICODE_LIMIT = 0x80;
    private File file;

    public Parser(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        return getContent(false);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(true);
    }

    private String getContent(boolean withoutUnicode) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = inputStream.read()) > 0) {
            if (!withoutUnicode) {
                output += (char) data;
                continue;
            }

            if (data < UNICODE_LIMIT) {
                output += (char) data;
            }
        }
        return output;
    }


    public void saveContent(String content) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            outputStream.write(content.charAt(i));
        }
    }
}
