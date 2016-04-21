import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public String getContent() throws IOException {
        FileInputStream i = new FileInputStream(file);
        return readData(i, new DefaultFilter());
    }

    public String getContentWithoutUnicode() throws IOException {
        FileInputStream i = new FileInputStream(file);
        return readData(i, new UnicodeFilter());
    }

    private synchronized String readData(FileInputStream i, Filter filter) throws IOException {
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            Character dataOut = filter.filter(data);
            if (dataOut != null) {
                output += dataOut;
            }
        }
        return output;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (FileOutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
    }
}
