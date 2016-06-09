import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 6/8/16
 */
//TODO Basically this class can be removed since it only delegates to Files.write
public class StringToFileWriter {
    private final Path dstPath;
    private final String data;

    public StringToFileWriter(String dst, String data) {
        Path dstPath = Paths.get(dst);

        if (Files.isDirectory(dstPath))
            throw new IllegalArgumentException(dst + " is a directory.");

        this.dstPath = dstPath;
        this.data = data;
    }

    /**
     * Writes data as bytes array into the specified file.
     *
     * @throws IOException
     */
    public void write() throws IOException {
        Files.write(dstPath, data.getBytes());
    }
}
