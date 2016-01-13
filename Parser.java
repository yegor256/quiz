import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * This class is thread safe.
 */
public class Parser {

    private final File file;

    public Parser(File file) throws IOException {
        //TODO: maybe add some null-check
        this.file = file;
        if (!file.exists()) {       // or any other approach, e.g throwing exception if not exists
            file.createNewFile();
        }
    }

    public String getContent() throws IOException {
        return getContentWithUnicode(true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContentWithUnicode(false);
    }

    private String getContentWithUnicode(boolean withUnicode) throws IOException {
        FileInputStream i = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();
        int data;
        synchronized (this) {       // to avoid dirty-read
            while ((data = i.read()) > 0) {
                if (withUnicode) {
                    sb.append((char) data);
                } else {
                    if (data < 0x80) {
                        sb.append((char) data);
                    }
                }
            }
        }
        return sb.toString();
    }

    public void saveContent(String content) throws IOException {
        if (content == null) {
            return;     // or exception, or build empty string to clear content of the file
        }
        synchronized (this) {   // only one thread writes to the file at the moment
            try (FileOutputStream o = new FileOutputStream(file)) {     // maybe the use of BufferedOutputStream would be more desirable
                o.write(content.getBytes());
                o.flush();
            } catch (IOException e) {
                throw e;    // or other handling
            }
        }
    }
}
