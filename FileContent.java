
package quiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * I change Parser to FileContent because
 * Parser isn't an object and I avoid class
 * using -er.
 *
 * @author Display Name (email@domain.com)
 * @version $Id$
 * @since 0.0
 */
final class FileContent {

    private static File file;

    /**
     * Because we're not providing any instance members.
     */
    private FileContent() {
        throw new AssertionError("Instantiating utility class...");
    }

    /**
     * Setting file.
     * @param infile File want to be set.
     */
    public static synchronized void setFile(final File infile) {
        file = infile;
    }

    /**
     * Getting file.
     * @return File.
     */
    public static synchronized File getFile() {
        return file;
    }

    /**
     * Getting file content.
     * @return String file content.
     * @throws IOException.
     */
    public static String getContent() throws IOException {
        final FileInputStream instr = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = instr.read()) > 0) {
            output += (char) data;
        }
        return output;
    }

    /**
     * Getting file content without unicode.
     * @return String file content without unicode.
     * @throws IOException.
     */
    public static String getContentWithoutUnicode() throws IOException {
        final FileInputStream instr = new FileInputStream(file);
        String output = "";
        int data;
        final long datalimit = 0x80;
        while ((data = instr.read()) > 0) {
            if (data < datalimit) {
                output += (char) data;
            }
        }
        return output;
    }

    /**
     * Saving file content.
     * @param content File content.
     * @throws IOException.
     */
    public static void saveContent(final String content)
    throws IOException {
        final FileOutputStream outstr = new FileOutputStream(file);
        for (int pos = 0; pos < content.length(); pos += 1) {
            outstr.write(content.charAt(pos));
        }
    }
}
