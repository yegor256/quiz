package read;

import java.io.*;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Objects;

public class ConcreteFileContents implements FileContents {

    private final File file;

    public ConcreteFileContents(final Path path) {
        this(path.toFile());
    }

    public ConcreteFileContents(final String file) {
        this(new File(file));
    }

    public ConcreteFileContents(final File file) {
        this.file = Objects.requireNonNull(file);
    }

    @Override
    public String asString() {
        final StringBuilder result = new StringBuilder();
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file)))) {
            final Iterator<String> lines = reader.lines().iterator();
            while (lines.hasNext()) {
                result.append(lines.next());
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return result.toString();
    }
}
