package read;

import java.util.Objects;

public class AsciiOnlyFileContents implements FileContents {

    private final FileContents original;

    public AsciiOnlyFileContents(final FileContents original) {
        this.original = Objects.requireNonNull(original);
    }

    @Override
    public String asString() {
        return this.original.asString().replaceAll("[^\\u0000-\\u007F]+", "");
    }
}
