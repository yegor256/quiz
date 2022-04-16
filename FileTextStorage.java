import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTextStorage implements Storage {
    private final TextSource source;
    private final File destination;

    public FileTextStorage(final File destination, final TextSource source) {
        this.destination = destination;
        this.source = source;
    }

    public FileTextStorage(final File destination, final String content) {
        this(destination, () -> content);
    }

    @Override
    public void save() throws IOException {
        final FileOutputStream o = new FileOutputStream(destination);
        final String content = this.source.getContent();
        try {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
