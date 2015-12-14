import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    public Parser(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return getContentWithoutUnicode(false)
    }

    public String getContentWithoutUnicode(boolean skipUnicode) throws IOException {
        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > skipUnicode ? 0x80 : 0) {
            output += (char) data;
        }
        return output;
    }

    /**
     * This method is not thread safe. If 2 threads will invoke this method - exception will be rised.
     */
    public void saveContent(String content) throws IOException {
        FileOutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}
