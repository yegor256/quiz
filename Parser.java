import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is thread safe.
 */
final public class Parser {

    /**
     * This is our internal File instance. Can be set only once.
     */
    private final File file;

    /**
     * Creates a Parser instance.
     * @param file The file to process. Nulls not supported.
     * @throws NullPointerException If the file argument is null. 
     */
    public Parser(File file) {
        if(file==null){
            throw new NullPointerException();
        }
        this.file = file;
    }

    /**
     * @return The internal File instance.
     */
    public File getFile() {
        return file;
    }

    /**
     * Read the contents of the file and return it as a String. No filtering.
     * @return A string with the file contents.
     * @throws IOException In case the IO operation went wrong.
     */
    synchronized public String getContent() 
    throws IOException {
        return getContentInternal(false);
    }

    /**
     * Read the contents of the file and return it as a String. Only bytes under
     * 0x80 are added to the resulting string; all other bytes are ignored.
     * @return A string with the file contents. Only characters representing bytes under
     * 0x80 in the resulting string.
     * @throws IOException In case the IO operation went wrong.
     */
    synchronized public String getContentWithoutUnicode() 
    throws IOException {
        return getContentInternal(true);
    }

    /**
     * Read the contents of the file and return it as a String. When
     * shouldSkipUnicode set to true only characters representing bytes under
     * 0x80 are returned. Otherwise - all characters returned.
     * @param shouldSkipUnicode "true" means only characters under 0x80 should
     * be returned.
     * @return A string with the file contents. Filtering depends on the
     * shouldSkipUnicode parameter.
     * @throws IOException In case the IO operation went wrong. 
     */
    // TODO Find out what is the REAL PURPOSE of this method. We are reading
    // bytes and converting them to characters manually! Looks strange.
    // TODO Avoid appending characters to String. Use StringBuilder.
    // TODO Use try/finally and close the FileInputStream "i" in the "finally"
    // block
    // TODO change 0x80 to a constant
    private String getContentInternal(boolean shouldSkipUnicode)
    throws IOException {
        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (shouldSkipUnicode) {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
            else {
                output += (char) data;
            }
        }
        return output;
    }

    /**
     * Writes the content argument into the file.
     * @param content A string to write into file.
     * @throws IOException In case the IO operation went wrong.
     */
    synchronized public void saveContent(String content) 
    throws IOException {
        PrintWriter out = new PrintWriter(file);
        try {
            out.print(content);
        } finally {
            out.close();
        }
    }
}
