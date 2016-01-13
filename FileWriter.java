import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by daniel on 13.01.2016.
 */
public class FileWriter implements Writer {

    public void write(File file, String content) throws IOException {
        if (content == null) {
            return;     // or exception, or build empty string to clear content of the file
        }
        try (FileOutputStream o = new FileOutputStream(file)) {     // maybe the use of BufferedOutputStream would be more desirable
            o.write(content.getBytes());
            o.flush();
        } catch (IOException e) {
            throw e;    // or other handling
        }
    }
}
