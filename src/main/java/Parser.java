import java.io.File;
import java.io.IOException;

/**
 * This is implementation of parser that support encoding filter.
 * Provide ability to read all content from the file.
 * Provide ability to read content without unicode from the file.
 * Provide ability to save string in the file.
 *
 * @author Eugene Katrechko
 * @version $Id$
 * @since 1.6
 */
public class Parser implements EncodingParser {
    private File file;

    public synchronized void setFile(File f) {
        this.file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public Parser() {
    }

    public Parser(File file) {
        this.file = file;
    }

    /**
     * Read file content to the String.
     *
     * @return A String with content
     * @throws IOException If an I/O error occurs
     */
    @Override
    public String getContent() throws IOException {
        return new ContentFileReader().readContent(this.file);
    }

    /**
     * Read file content without unicode to the String.
     *
     * @return A String with content without unicode
     * @throws IOException If an I/O error occurs
     */
    @Override
    public String getContentWithoutUnicode() throws IOException {
        return new ContentWithoutUnicodeFileReader(new ContentFileReader()).readContent(this.file);
    }

    /**
     * Writes string to a file.
     *
     * @param content A String for writing
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void saveContent(String content) throws IOException {
        new ContentFileWriter().writeContent(this.file, content);
    }
}
