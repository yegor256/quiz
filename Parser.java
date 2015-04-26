
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

    private final File file;
    private final FileInputStream fileStream;

    public Parser(final String filePath) throws FileNotFoundException {
        this.file = new File(filePath);
        this.fileStream = new FileInputStream(this.file);
    }

    public synchronized String getContent() throws IOException {
        final StringBuilder output = new StringBuilder();
        int data;
        while ((data = fileStream.read()) > 0) {
            output.append((char) data);
        }
        return output.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        final StringBuilder output = new StringBuilder();
        int data;
        while ((data = fileStream.read()) > 0) {
            if (data < 0x80) {
                output.append((char) data);
            }
        }
        return output.toString();
    }

    public synchronized void saveContent(final String content) throws IOException {
        final FileOutputStream o = new FileOutputStream(file);
        o.write(content.getBytes());
    }
}
