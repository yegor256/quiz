package write;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Objects;

public class WriteStringToFile implements WriteToFile {

    private final File file;
    private final String value;

    public WriteStringToFile(final Path path, final String value) {
        this(path.toFile(), value);
    }

    public WriteStringToFile(final String file, final String value) {
        this(new File(file), value);
    }

    public WriteStringToFile(final File file, final String value) {
        this.file = Objects.requireNonNull(file);
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public void write() {
        try (final FileWriter fileWriter = new FileWriter(this.file)) {
            fileWriter.write(this.value);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
