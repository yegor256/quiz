import java.io.File;
import java.io.IOException;

/**
 * This is interface for content reader.
 * Return content in String.
 *
 * @author Eugene Katrechko
 * @version $Id$
 * @since 1.6
 */
public interface ContentReadable {
    /**
     * @param file File for reading
     * @return A String with content
     * @throws IOException If an I/O error occurs
     */
    String readContent(File file) throws IOException;
}
