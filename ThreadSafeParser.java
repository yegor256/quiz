import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class ThreadSafeParser {


    /** The file to read/write content to and from */
    private File file;


    public synchronized void setFile(File file) {
        this.file = file;
    }


    public synchronized File getFile() {
        return file;
    }


    /**
     * Returns file output.
     * @return the string content of the file.
     * @throws IOException
     */
    public String getContent() throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = fileInputStream.read()) > 0) {
            output += (char) data;
        }
        return output;
    }


    /**
     * Rerturns content without unicode.
     * @return the string content of the file without unicode.
     * @throws IOException
     */
    public String getContentWithoutUnicode() throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = fileInputStream.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }


    /**
     * Saves the provided string to the file.
     * @param content
     * @throws IOException
     */
    public void saveContent(final String content) throws IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        for (final int ii = 0; ii < content.length(); ii += 1) {
            fileOutputStream.write(content.charAt(ii));
        }
    }

}
