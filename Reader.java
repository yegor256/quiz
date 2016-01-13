import java.io.File;
import java.io.IOException;

/**
 * Created by daniel on 13.01.2016.
 */
public interface Reader {

    public String read(File file) throws IOException;
}
