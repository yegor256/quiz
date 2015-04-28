import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
    private final File file;

    public Parser(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        FileInputStream i = new FileInputStream(file);
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = i.read()) > 0) {
            output.append((char) data);
        }
        i.close();
        return output.toString();
    }

    public String getContentWithoutUnicode() throws IOException {
        FileInputStream i = new FileInputStream(file);
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output.append((char) data);
            }
        }
        i.close();
        return output.toString();
    }

    public void saveContent(String content) throws IOException {
        FileOutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
        o.close();
    }
}
