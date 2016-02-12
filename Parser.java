import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is not thread safe.
 */
public class Parser {

    public String getContent(File file, boolean withUnicode) throws IOException {
        FileInputStream i = new FileInputStream(file); // Convert to a buffered reader
        String output = ""; // convert to string builder
        int data;
        while ((data = i.read()) > 0) {


            // look for a better way of checking for unicode character 
            if (withUnicode && data >= 0x80) {
                output += (char) data;
            } else if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }

    public void saveContent(File file, String content) throws IOException {
        FileOutputStream o = new FileOutputStream(file); // buffered writer
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}
