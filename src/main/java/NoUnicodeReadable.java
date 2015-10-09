import java.io.IOException;

/**
 * This is implementation of interface for content reader.
 * Provide ability to read content from the file.
 *
 * @author Eugene Katrechko
 * @version $Id$
 * @since 1.6
 */
public class NoUnicodeReadable implements Readable {
    Readable readable;

    public NoUnicodeReadable(Readable readable) {
        this.readable = readable;
    }

    /**
     * @return A String with content without Unicode
     * @throws IOException If an I/O error occurs
     */
    @Override
    public String read() throws IOException {
        return this.readable.read().replaceAll("[^\\u0000-\\uFFFF]", "");
    }
}
