import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {

    private static final int READ_BUFFER_SIZE = 512;
    private final File file;

    public Parser(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    /**
     * Get the parsed file content with unicode characters. This method assumes the
     * file is UTF-8 encoded.
     *
     * @return the file content with unicode chars.
     * @throws IOException
     */
    public String getContent() throws IOException {
        return getContent(true, "UTF-8");
    }

    /**
     * Get the parsed file content without unicode characters. This method assumes the
     * file is UTF-8 encoded.
     * @return the file content without unicode chars.
     * @throws IOException
     */
    public String getContentWithoutUnicode() throws IOException {
        return getContent(false, "UTF-8");
    }


    /**
     * Parse a file content into a String.
     * @param withUnicode if it should include unicode characters.
     * @param encoding The charset encoding.
     * @return the file content.
     * @throws IOException
     */
    public String getContent(boolean withUnicode, String encoding) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
        try {
            StringBuilder output = new StringBuilder();
            char[] buffer = new char[READ_BUFFER_SIZE];
            int readCount;
            while ((readCount = in.read(buffer)) != -1) {
                for (int i = 0; i < readCount; i++) {
                    // Note: the extended ASCII spec goes up to 0xFF, but I'm assuming the
                    // 7-bit version is part of the requirements.
                    if (withUnicode || ((int)buffer[i]) < 0x80) {
                        output.append(buffer[i]);
                    }
                }
            }
            return output.toString();
        } finally {
            in.close();
        }
    }


    /**
     * Save the string into the file. This method assumes the file encoding to be UTF-8.
     * @param content the content to save
     * @throws IOException
     */
    public void saveContent(String content) throws IOException {
       saveContent(content, "UTF-8");
    }

    /**
     * Save the string into the file.
     * @param content the content to save.
     * @param encoding the output file encoding.
     * @throws IOException
     */
    public void saveContent(String content, String encoding) throws IOException {
        BufferedWriter o = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
        try {
            o.write(content);
        } finally {
            o.close();
        }
    }
}
