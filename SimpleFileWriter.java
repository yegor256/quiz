import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Objects;

public class SimpleFileWriter implements FileWriter {
    private final File file;
    private final Charset charset;

    public SimpleFileWriter(File file, Charset charset) {
        this.file = Objects.requireNonNull(file, "file must be non-null");
        this.charset = Objects.requireNonNull(charset, "charset must be non-null");
    }

    @Override
    public File file() {
        return file;
    }

    @Override
    public void saveContent(String content) throws IOException {
        try (final BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset)) {
            writer.write(content);
        }
    }
}
