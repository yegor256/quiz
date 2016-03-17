import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
    private static final String EMPTY_STRING = "";
    private File file;

    public Parser(File file) {

        this.file = file;
    }

    public synchronized String getContent() throws IOException {

        FileInputStream fileInputStream = null;
        StringBuilder output = new StringBuilder(EMPTY_STRING);
        try {
            fileInputStream = new FileInputStream(file);

            int data;
            while ((data = fileInputStream.read()) > 0) {
                output.append((char) data);
            }
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
        return output.toString();
    }

    public String getContentWithoutUnicode() throws IOException {

        String content = this.getContent();
        StringBuilder output = new StringBuilder(EMPTY_STRING);
        for (char c : content.toCharArray()) {
            if (c < 0x80) {
                output.append(c);
            }
        }
        return output.toString();
    }

    public synchronized void saveContent(String content) throws IOException {

        FileOutputStream fileOutputStream = null;
        try {

            fileOutputStream = new FileOutputStream(file);
            for (int i = 0; i < content.length(); i += 1) {
                fileOutputStream.write(content.charAt(i));
            }
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }
}
