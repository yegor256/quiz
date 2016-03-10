import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {

    public  String getContent(File input, boolean unicode) throws IOException {

        String output = "";
        FileInputStream i = null;

        try {
            i = new FileInputStream(input);
            int data;
            while ((data = i.read()) > 0) {

                if (!unicode || (data < 0x80)) {
                    output += (char) data;
                }
            }
        } finally {
            i.close();
        }

        return output;
    }

    public  void saveContent(String content, File output) throws IOException {
        FileOutputStream o = null;

        try {
            o = new FileOutputStream(output);

            for (int i = 0; i < content.length(); i ++) {
                o.write(content.charAt(i));
            }
        } finally {
            o.close();
        }
    }
}