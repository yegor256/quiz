import java.io.*;
import java.util.regex.Pattern;

/**
 * This class is thread safe.
 */
public class Parser {
    private volatile File file;
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final Pattern REGEX_NON_ASCII = Pattern.compile("[^\\x00-\\x7F]");

    public void setFile(File f) {
        file = f;
    }

    public File getFile() {
        return file;
    }

    /**
     * @return unfiltered content of the file
     * @throws IOException
     */
    public String getContent() throws IOException {
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(input);
            StringWriter writer = new StringWriter();
            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            while (reader.read(buffer) > 0) {
                writer.write(buffer);
            }
            return writer.toString();
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * @return filtered content of the file - only ASCII symbols
     * @throws IOException
     */
    public String getContentWithoutUnicode() throws IOException {
        return REGEX_NON_ASCII.matcher(getContent()).replaceAll("");
    }

    /**
     * Saves data to the file
     *
     * @param content String data to save into file
     * @throws IOException
     */
    public void saveContent(String content) throws IOException {
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(file);
            o.write(content.getBytes());
        } finally {
            if (o != null) {
                o.close();
            }
        }
    }
}
