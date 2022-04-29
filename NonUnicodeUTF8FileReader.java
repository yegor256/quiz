import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

public class NonUnicodeUTF8FileReader implements FileReader {
    private static final int MAX_ASCII_CODE = 127;
    private final File file;

    public NonUnicodeUTF8FileReader(File file) {
        Objects.requireNonNull(file, "file must be non-null");
        this.file = file;
    }

    @Override
    public File file() {
        return file;
    }

    @Override
    public String readContent() throws IOException {
        final StringBuilder result = new StringBuilder();
        try (final InputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
            boolean nextIsPartOfMultiByteCharacter = false;
            int code;
            while ((code = inputStream.read()) != -1) {
                if (code > MAX_ASCII_CODE) {
                    nextIsPartOfMultiByteCharacter = true;
                } else {
                    if (nextIsPartOfMultiByteCharacter) {
                        nextIsPartOfMultiByteCharacter = false;
                    } else {
                        result.append((char)code);
                    }
                }
            }
        }
        return result.toString();
    }
}
