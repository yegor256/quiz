import java.io.File;
import java.io.IOException;

/**
 * Created by daniel on 13.01.2016.
 */
public interface Writer {

    public void write(File file, String content) throws IOException;
}
