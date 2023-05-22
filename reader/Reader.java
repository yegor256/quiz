package reader;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * @author madrabit on 15.10.2020
 * @version 1$
 * @since 0.1
 */
public interface Reader {
    String getContent(StringBuilder output, BufferedInputStream i) throws IOException;
}
