import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

    private final File file;

    /**
     * Initialize new parser with file.
     */
    public Parser(File f) {
        this.file = f;
    }

    /**
     * Get the file parser had been initialized with.
     *
     * @return File descriptor.
     */
    public File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        FileInputStream i = new FileInputStream(file);
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = i.read()) > 0) {
            output.append((char) data);
        }
        return output.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        FileInputStream i = new FileInputStream(file);
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output.append((char) data);
            }
        }
        return output.toString();
    }

    //TODO: Move this method into separate file to provide interface segregation.
    /**
     * Saves content string into the file that the parser had been initialized with.
     *
     * @param content Content string.
     * @throws IOException                        If there are errors writing content to the disc.
     * @throws java.lang.IllegalArgumentException If content string is {@code null}
     */
    public synchronized void saveContent(String content) throws IOException {
        if (content == null) {
            throw new IllegalArgumentException("Content string shouldn't be null");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
    }
}
