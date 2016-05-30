import java.io.IOException;

public class NoUnicodeTextFile implements TextFile {

    private final TextFile origin;

    public NoUnicodeTextFile(TextFile textFile) {
        this.origin = textFile;
    }

    @Override
    public String read() throws IOException {
        String content = origin.read();
        return content.replaceAll("[^\\p{ASCII}]", "");
    }

    @Override
    public void write(String content) throws IOException {
        origin.write(content);
    }
}
