import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Objects;

public class SimpleFileReader implements FileReader {
    private final File file;
    private final Charset charset;

    public SimpleFileReader(File file, Charset charset) {
        this.file = Objects.requireNonNull(file, "file must be non-null");
        this.charset = Objects.requireNonNull(charset, "charset must be non-null");
    }

    @Override
    public File file() {
        return file;
    }

    @Override
    public String readContent() throws IOException {
        final byte[] fileAsBytes = Files.readAllBytes(file.toPath());
        return new String(fileAsBytes, charset);
    }
}
