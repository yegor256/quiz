import java.io.IOException;

public final class WithoutUnicodeFileText implements Text {

    private final Text origin;

    public WithoutUnicodeFileText(final Text text) {
        this.origin = text;
    }

    @Override
    public synchronized String read() throws IOException {
        final StringBuilder output = new StringBuilder("");
        final String text = this.origin.read();
        for (final char symbol : text.toCharArray()) {
            if (WithoutUnicodeFileText.asciiSymbol(symbol)) {
                output.append(symbol);
            }
        }
        return output.toString();
    }

    @Override
    public synchronized void write(final String content) throws IOException {
        this.origin.write(content);
    }

    private static boolean asciiSymbol(final char symbol) {
        return symbol < 0x80;
    }
}
