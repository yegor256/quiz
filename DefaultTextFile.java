import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DefaultTextFile implements TextFile {
    private final File file;

    public DefaultTextFile(File file) {
        this.file = file;
    }

    @Override
    public String read() throws IOException {
        return new String(
                Files.readAllBytes(file.toPath())
        );
    }

    @Override
    public void write(String content) throws IOException {
        Files.write(file.toPath(), content.getBytes());
    }
}
