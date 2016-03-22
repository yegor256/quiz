import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Writes small amount of data.
 */
public class SmallDataWriter {
    public void writeContent(Writer writer, String content) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(content);
        }
    }
}