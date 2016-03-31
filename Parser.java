import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {

    private File file;

    public Parser(File f) {
        this.file = f;
    }

    public File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        return getFilteredContent(null);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getFilteredContent(new UnicodeFilter());
    }

    public synchronized void saveContent(String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
        writer.write(content);
    }

    public synchronized String getFilteredContent(Filter f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.file));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (f != null) {
                line = f.filter(line);
            }
            builder.append(line);
        }
        return builder.toString();
    }

    public interface Filter {
        String filter(String s);
    }

    class UnicodeFilter implements Filter {
        public String filter(String s) {
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
}
