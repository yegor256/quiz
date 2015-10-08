import java.io.File;
import java.io.IOException;

/**
 * This is interface for content writer.
 * Save String content.
 *
 * @author Eugene Katrechko
 * @version $Id$
 * @since 1.6
 */
public interface ContentWritable {
    /**
     * @param file    File for writing
     * @param content String for writing
     * @throws IOException If an I/O error occurs
     */
    void writeContent(File file, String content) throws IOException;
}
