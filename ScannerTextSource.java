import java.io.IOException;
import java.util.Scanner;

public class ScannerTextSource implements TextSource {
    private final Scanner source;

    public ScannerTextSource(final Scanner source) {
        this.source = source;
    }

    @Override
    public String getContent() throws IOException {
        return this.source.nextLine();
    }
}
