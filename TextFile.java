import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TextFile implements Text {
    private final File file;

    public TextFile(File f) {
        this.file = f;
    }
    @Override
    public String content() {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("read file [%s] error. message [%s]", file.getAbsolutePath(), e.getMessage()));
        }
    }

    @Override
    public void saveContent(String content) {
        try {
            Files.write(file.toPath(), content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
