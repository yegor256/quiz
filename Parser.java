import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * This class is thread safe.
 */
public class Parser {
    private volatile File file;

    public void setFile(File f) {
        file = f;
    }

    public File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new String(bytes);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent().replaceAll("\\P{Print}", "");
    }

    public synchronized void saveContent(String content) throws IOException {
        Files.write(file.toPath(), content.getBytes(), StandardOpenOption.CREATE);
    }
}
