import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

    public static String getContent(File file, boolean withoutUnicode) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            String output = "";
            int data;
            while ((data = fileInputStream.read()) > 0) {
                if (withoutUnicode && data < 0x80) {
                    output += (char) data;
                } else if (!withoutUnicode) {
                    output += (char) data;
                }
            }
            return output;
        } catch (IOException e) {
            throw new IOException("getContentException", e);
        }
    }

    public static File saveContent(String fn, String content) throws IOException {
        File file = new File(fn);
        try (FileOutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
            return file;
        } catch (IOException e) {
            throw new IOException("saveContentException", e);
        }
    }
}
