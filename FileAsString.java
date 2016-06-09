import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class preserves a snapshot of the specified file as a string encoded in UTF-8.
 * <p/>
 * This class is designed to be created once and then used many times, i.e. slow at construction fast at execution.
 * <p/>
 * This class won't handle large files.
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 6/8/16
 */
//TODO basing on the knowledge acquired from Yegor's presentation at JPoint2016 I believe this class should look like this
//TODO otherwise I would prefer simple utility class with static method aka FilesEx.readAsString(Path, boolean):String
public class FileAsString {
    private final String value;
    private final String valueWithBasicUnicode;

    /**
     * @param src source file name/path
     * @throws IllegalArgumentException if src does not exist or is not a file
     */
    public FileAsString(String src) throws IOException {
        Path srcPath = Paths.get(src);

        if (!Files.isRegularFile(srcPath))
            throw new IllegalArgumentException(src + " does not exist or not a file.");

        byte[] bytes = Files.readAllBytes(srcPath);
        this.value = new String(bytes, "UTF-8");
        this.valueWithBasicUnicode = this.value.replaceAll("[^\\u0000-\\u007F]", "");
    }

    /**
     * @return a string represents the file's content.
     */
    public String get() {
        return value;
    }

    /**
     * Filters file's bytes by value 0x80, i.e. only those bytes which are less than the value will end up in the
     * returned result.
     *
     * @return a string which chars are in the range [0x00;0x80)
     */
    public String getBasicUnicode() {
        return valueWithBasicUnicode;
    }

    /**
     * @param src source file
     * @throws IOException
     * @throws IllegalArgumentException
     */
    //TODO I would throw this constructor away
    public FileAsString(File src) throws IOException {
        this(src.getAbsolutePath());
    }
}
