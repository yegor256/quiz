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
        return getContent(false)
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(true);
    }

    private String getContent(boolean skipUnicode){
        FileInputStream i = new FileInputStream(file);
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = i.read()) > skipUnicode ? 0x80 : -1) {
            output.append((char)data);
        }
        return output.toString();
    }

    /**
     * This method is not thread safe. If 2 threads will invoke this method - exception will be rised.
     */
    public synchronized void saveContent(String content) throws IOException {
        FileOutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}
