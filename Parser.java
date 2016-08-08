import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {

    private static final int UPPER_ASCII_CHAR_BOUND = 0x80;

    private final File file;

    public Parser(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        Reader inputStreamReader = new InputStreamReader(
                new FileInputStream(file)
        );
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = inputStreamReader.read()) > 0) {
            output.append((char) data);
        }
        inputStreamReader.close();
        return output.toString();
    }

    public String getContentWithoutUnicode() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = fileInputStream.read()) > 0) {
            if (data < UPPER_ASCII_CHAR_BOUND) {
                output.append((char) data);
            }
        }
        fileInputStream.close();
        return output.toString();
    }

    public void saveContent(String content) throws IOException {
        Writer outputStreamWriter = new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"
        );
        for (int i = 0; i < content.length(); i++) {
            outputStreamWriter.write(content.charAt(i));
        }
        outputStreamWriter.close();
    }

}
