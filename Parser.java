import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

    public String getContent(FileInputStream is) throws IOException {
        return new CommonReader().read(is);
    }

    public String getContentWithoutUnicode(FileInputStream is) throws IOException {
        return new AsciiReader().read(is);
    }

}