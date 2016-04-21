import java.io.*;
import java.nio.file.Files;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
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
