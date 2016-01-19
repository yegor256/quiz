import java.io.FileInputStream;
import java.io.IOException;

/**
 * AbstractReader
 *
 * @author Alexander Kontsur (a.kontsur)
 * @since 19.01.2016
 */
public abstract class AbstractReader implements Reader {

    @Override
    public String read(FileInputStream is) throws IOException {
        if (is == null || is.available() == 0) {
            throw new IllegalArgumentException("Stream is not ready");
        }
        StringBuilder output = new StringBuilder();
        try {
            int data;
            while ((data = is.read()) > 0) {
                apply(output, data);
            }
        } finally {
            is.close();
        }
        return output.toString();
    }

    protected abstract void apply(StringBuilder sb, int data);

}
