import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Function;

/**
 * This class is thread safe.
 */
public class Parser {
    public static final int ASCII_VALUE = 0x80;
    private final File file;

    public Parser(final File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        return getContentWithFilter(c -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContentWithFilter(c -> c < ASCII_VALUE);
    }

    private String getContentWithFilter(final Function<Integer, Boolean> filterFunction) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        try (final InputStream inputStream = new FileInputStream(file)) {
            int data;
            while ((data = inputStream.read()) > 0) {
                if (filterFunction.apply(data)) {
                    stringBuilder.append((char) data);
                }
            }
        }
        return stringBuilder.toString();
    }

    public synchronized void saveContent(final String content) throws IOException {
        try (final OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(content.getBytes());
        }
    }

}