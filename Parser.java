import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    public Parser() {
        this(null);
    }

    public Parser(File f) {
        file = f;
    }

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        return readFile(new FileInputStream(file), false);
    }

    public String getContentWithoutUnicode() throws IOException {
        return readFile(new FileInputStream(file), true);
    }

    // TODO: make sure the file handle gets closed when an exception occurs?
    private String readFile(FileInputStream i, boolean ignoreUnicode) throws IOException {
        StringBuilder output = new StringBuilder();
        byte[] buf = new byte[4096]; // make buffer size configurable?
        int read;
        while ((read = i.read(buf)) > -1) {
            if (ignoreUnicode) {
                for (int b = 0; b < buf.length; b++) {
                    if (buf[b] < 0x80) {
                        output.append(buf[b]);
                    }
                }
            } else {
                output.append(new String(buf, 0, read));
            }
        }
        i.close();
        return output.toString();
    }

    public void saveContent(String content) throws IOException {
        FileOutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
        o.flush(); // TODO: verify if this call is redundant
        o.close();
    }
}
