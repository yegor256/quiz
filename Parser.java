import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * This class is thread safe.
 */
public class Parser {

    private final File _file;

    public Parser(File file)
    {
        _file = file;
    }

    public String getContent() throws IOException {
        synchronized (_file) {
            return new String(Files.readAllBytes(_file.toPath()));
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent().replaceAll("\\P{InBasic_Latin}", "");
    }

    public void saveContent(String content) throws IOException {
        synchronized (_file) {
            Files.write(_file.toPath(), content.getBytes(), StandardOpenOption.WRITE);
        }
    }
}