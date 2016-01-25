import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

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

    public synchronized String getContent(boolean isUnicode) throws IOException {

        try (BufferedReader br =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(file), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            int data;
            while ((data = br.read()) != -1) {
                if (isUnicode) {
                    sb.append((char) data);
                } else if (data < 0x80) {
                    sb.append((char) data);
                }
            }
            return sb.toString();
        }
    }

    public synchronized void saveContent(String content) throws IOException {
        try (OutputStreamWriter o =
                     new OutputStreamWriter(
                             new FileOutputStream(file), StandardCharsets.UTF_8)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
    }
}
