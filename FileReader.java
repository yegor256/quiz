import java.io.File;
import java.io.IOException;

public interface FileReader {
    File file();

    String readContent() throws IOException;
}
