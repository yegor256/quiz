import java.io.IOException;

/**
 * This is implementation of interface for content reader.
 * Provide ability to read content from the file.
 *
 * @author Eugene Katrechko
 * @version $Id$
 * @since 1.6
 */
public class WithoutUnicodeFileReader implements Readable {
    Readable contentReader;

    public WithoutUnicodeFileReader(Readable contentReader) {
        this.contentReader = contentReader;
    }

    /**
     * @return A String with content without Unicode
     * @throws IOException If an I/O error occurs
     */
    @Override
    public String readContent() throws IOException {
        return this.contentReader.readContent().replaceAll("[^\\u0000-\\uFFFF]", "");
    }

}
