import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by daniel on 13.01.2016.
 */
public class FileReader implements Reader {

    public String read(File file) throws IOException {
        FileInputStream i = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();
        int data;
        while ((data = i.read()) > 0) {
            sb.append((char) data);
        }
        return sb.toString();
    }
}
