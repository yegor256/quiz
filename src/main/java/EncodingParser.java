import java.io.IOException;

/**
 * This is interface for parser that support encoding.
 * Provide ability to read all content.
 * Provide ability to read content without unicode.
 * Provide ability to save string.
 *
 * @author Eugene Katrechko
 * @version $Id$
 * @since 1.6
 */
public interface EncodingParser {
    /**
     * Read file content to the String.
     *
     * @return A String with content
     * @throws IOException If an I/O error occurs
     */
    String getContent() throws IOException;

    /**
     * Read file content without unicode to the String.
     *
     * @return A String with content without unicode
     * @throws IOException If an I/O error occurs
     */
    String getContentWithoutUnicode() throws IOException;

    /**
     * Writes string to a file.
     *
     * @param content A String for writing
     * @throws IOException If an I/O error occurs
     */
    void saveContent(String content) throws IOException;
}
