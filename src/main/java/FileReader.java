import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This is implementation of interface for file reader.
 * Provide ability to read content from the file.
 *
 * @author Eugene Katrechko
 * @version $Id$
 * @since 1.6
 */
public class FileReader implements Readable {
    private File file;

    public FileReader(File file) {
        this.file = file;
    }

    /**
     * @return A String with content
     * @throws IOException If an I/O error occurs
     */
    @Override
    public String read() throws IOException {
        StringBuilder outputString = new StringBuilder();
        try (BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8)))
        {
            while (true) {
                int data = bufReader.read();
                if (data == -1) break; // exit point
                outputString.append((char) data);
            }
        }
        return outputString.toString();
    }
}
