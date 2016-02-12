import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */

public class Parser {

    public static String getContent(File file, boolean withUnicode) throws IOException {
        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (withUnicode) {
                output += (char) data;
            } else {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
        }
        return output;
    }

    public static void saveContent(String content, File file) throws IOException {
        FileOutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}
