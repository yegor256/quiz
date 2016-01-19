import java.io.FileInputStream;
import java.io.IOException;

/**
 * Reader
 *
 * @author Alexander Kontsur (a.kontsur)
 * @since 19.01.2016
 */
public interface Reader {

    String read(FileInputStream is) throws IOException;

}
