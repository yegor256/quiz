import java.io.*;

/**
 * Generic interface for a Parser.
 */
interface Parser<T> {

    T getContent() throws IOException;

    void setContent(T content) throws IOException;
}

/**
 * Parser that reads/writes text (using default format - UTF-8) from/to a File.
 * This class is thread safe.
 */
class TextFileParser implements Parser<String> {

    private File file;

    public TextFileParser(File f) {
        this.file = f;
    }

    public File getFile() {
        return this.file;
    }

    synchronized public String getContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.getFile()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }

    synchronized public void setContent(String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.getFile()));
        writer.write(content);
        writer.close();
    }
}

/**
 * Parser that reads/writes ascii text from/to a File.
 * This class is thread safe.
 */
class AsciiFileParser extends TextFileParser {

    public AsciiFileParser(File f) {
        super(f);
    }

    synchronized public String getContent() throws IOException {
        return filterUnicode(super.getContent());
    }

    /**
     * @implNote I know that this behaviour was not on the original implementation,
     * but, since I wrote that this class reads/writes ascii text, lets make sure that we only write
     * ascii text to the File.
     */
    synchronized public void setContent(String content) throws IOException {
        super.setContent(filterUnicode(content));
    }

    private String filterUnicode(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < 0x80) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

}