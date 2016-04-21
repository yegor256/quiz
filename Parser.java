import java.io.*;
import java.nio.file.Files;

/**
 * This class is thread safe.
 */
public final class Parser {

    private final File file;

    public Parser(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent().replaceAll("[^\\x00-\\x7F]", "");
    }

    public void saveContent(String content) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(content);
        }
    }
}
