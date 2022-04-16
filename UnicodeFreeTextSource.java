import java.io.IOException;

public final class UnicodeFreeTextSource implements TextSource {
    private final TextSource origin;

    public UnicodeFreeTextSource(final TextSource origin) {
        this.origin = origin;
    }

    @Override
    public String getContent() throws IOException {
        final String source = origin.getContent();
        String result = "";
        for (final char character : source.toCharArray()) {
            if (character > 0x80) {
                continue;
            }
            result += character;
        }
        return result;
    }
}
