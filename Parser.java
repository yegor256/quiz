import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    /**
     * Get content filtered without unicode characters above 0x80.
     *
     * @return Filtered content.
     * @throws IOException Under error.
     */
    public String getContentWithoutUnicode() throws IOException {
        try (FileInputStream in = new FileInputStream(file)) {
            return Stream.of(in.read())
                    .filter(data -> data < 0x80)
                    .collect(Collector.of(
                            StringBuilder::new,
                            StringBuilder::appendCodePoint,
                            StringBuilder::append,
                            StringBuilder::toString));
        }
    }

    public void saveContent(String content) throws IOException {
        Files.write(file.toPath(), content.getBytes());
    }
}
