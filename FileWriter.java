import java.io.File;
import java.io.IOException;

public interface FileWriter {
    File file();

    void saveContent(String content) throws IOException;
}
