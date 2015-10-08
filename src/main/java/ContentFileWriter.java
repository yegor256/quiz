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
public class ContentFileWriter implements ContentWritable {
    /**
     * @param file    File for writing
     * @param content String for writing
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void writeContent(File file, String content) throws IOException {
        if (file == null) throw new IOException("File not found");
        try (BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8))) {
            bufWriter.write(content);
        }
    }
}








