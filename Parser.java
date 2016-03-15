import java.io.*;
import java.nio.file.Files;

/**
 * This class is thread safe.
 */
public class Parser {

    private final static String LINE_DELIMITER = "\n";
    private File file;

    public Parser(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized CharSequence getContent() throws IOException {
        StringBuilder output = new StringBuilder();
        Files.lines(file.toPath()).forEachOrdered(line -> output.append(line).append(LINE_DELIMITER));
        return output;
    }

    public synchronized CharSequence getContentWithoutUnicode() throws IOException {
        return getContent().toString().replaceAll("[^\\x00-\\x7F]", "");
    }

    public synchronized void saveContent(String content) throws IOException {
        try (FileOutputStream o = new FileOutputStream(file)) {
            o.write(content.getBytes());
        }
    }
}
