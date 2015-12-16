import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    public Parser(File f) {
        file = f;
    }

    public File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        try (FileInputStream i = new FileInputStream(file); InputStreamReader reader = new InputStreamReader(i);) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = reader.read()) > 0) {
                output.append((char) data);
            }
            return output.toString();
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        try (FileInputStream i = new FileInputStream(file); InputStreamReader reader = new InputStreamReader(i);) {

            StringBuilder output = new StringBuilder();
            int data;
            while ((data = reader.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
            reader.close();
            return output.toString();
        }
    }

    public void saveContent(String content) throws IOException {
        try (FileOutputStream o = new FileOutputStream(file); OutputStreamWriter writer = new OutputStreamWriter(o);) {
            for (int i = 0; i < content.length(); i += 1) {
                writer.write(content.charAt(i));
            }
        }
    }
}
