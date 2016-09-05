
package quiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * I change Parser to ParsedFile because
 * Parser isn't an object and I avoid class
 * using -er.
 *
 * @author Display Name (email@domain.com)
 * @version $Id$
 * @since 0.0
 */
final class ParsedFile {

    private File file;

    /**
     * Because we're not providing any instance members.
     * @param fil File input.
     */
    public void parsedFile(final File fil) {
        this.file = fil;
    }

    /**
     * Getting file.
     * @return File.
     */
    public static synchronized File fileOf() {
        return this.file;
    }

    /**
     * Getting content with options.
     * @param isunicode Option to return ascii or unicode content.
     * @return String file content.
     * @throws IOException.
     */
    public String content(final boolean isunicode) throws IOException {
        final FileInputStream stream = new FileInputStream(this.file);
        String output = "";
        int data;
        final long limit = 0x80;
        while ((data = stream.read()) > 0) {
            if (isunicode) {
                if (data < limit) {
                    output += (char) data;
                }
            } else {
                output += (char) data;
            }
        }
        return output;
    }

    /**
     * Getting content.
     * @return String file content without unicode.
     * @throws IOException.
     */
    public String content() throws IOException {
        return this.content(false);
    }

    /**
     * Getting unicode content.
     * @return String file content without unicode.
     * @throws IOException.
     */
    public String unicodeContent() throws IOException {
        return this.content(true);
    }

    /**
     * Saving file content.
     * @param content File content.
     * @throws IOException.
     */
    public void save(final String content)
    throws IOException {
        final FileOutputStream stream = new FileOutputStream(this.file);
        for (int pos = 0; pos < content.length(); pos += 1) {
            stream.write(content.charAt(pos));
        }
    }
}
