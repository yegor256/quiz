import java.io.File;
import java.io.IOException;

/**
 * This is implementation of interface for content reader.
 * Provide ability to read content from the file.
 *
 * @author Eugene Katrechko
 * @version $Id$
 * @since 1.6
 */
public class ContentWithoutUnicodeFileReader implements ContentReadable {
    ContentReadable contentReader;

    public ContentWithoutUnicodeFileReader(ContentReadable contentReader) {
        this.contentReader = contentReader;
    }

    /**
     * @param file File for reading
     * @return A String with content without Unicode
     * @throws IOException If an I/O error occurs
     */
    @Override
    public String readContent(File file) throws IOException {
        return this.contentReader.readContent(file).replaceAll("[^\\u0000-\\uFFFF]", "");
    }

}
