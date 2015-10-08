import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This is implementation of interface for file writer.
 * Provide ability to write string in the file.
 *
 * @author Eugene Katrechko
 * @version $Id$
 * @since 1.6
 */
public class FileWriter implements Writable {
    private File file;

    public FileWriter(File file) {
        this.file = file;
    }

    /**
     * @param content String for writing
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void write(String content) throws IOException {
        try (BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.file), StandardCharsets.UTF_8)))
        {
            bufWriter.write(content);
        }
    }
}








