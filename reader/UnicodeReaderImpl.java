package reader;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * @author madrabit on 15.10.2020
 * @version 1$
 * @since 0.1
 */
public class UnicodeReaderImpl implements Reader {
    @Override
    public String getContent(StringBuilder output, BufferedInputStream i) throws IOException {
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output.append((char) data);
            }
        }
        return output.toString();
    }
}
