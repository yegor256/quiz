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
public class ContentFileReader implements ContentReadable {
    /**
     * @param file File for reading
     * @return A String with content
     * @throws IOException If an I/O error occurs
     */
    @Override
    public String readContent(File file) throws IOException {
        BufferedReader bufReader = null;

        if (file == null) throw new IOException("File not found");
        StringBuilder outputString = new StringBuilder();
        try {
            bufReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), StandardCharsets.UTF_8));
            while (true) {
                int data = bufReader.read();
                if (data == -1) break; // exit point
                outputString.append((char) data);
            }
        } finally {
            if (bufReader != null) {
                bufReader.close();
            }
        }
        return outputString.toString();
    }
}
