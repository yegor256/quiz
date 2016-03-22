import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Reads small amount of data.
 */
public class SmallDataReader {
    private static final CharsetEncoder ENCODER = StandardCharsets.US_ASCII.newEncoder();

    public String readUnicodeContent(Reader reader) throws IOException {
        return read(reader, false);
    }

    public String readAsciiContent(Reader reader) throws IOException {
        return read(reader, true);
    }

    private static String read(Reader reader, boolean readAscii) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            StringBuilder result = new StringBuilder();
            int data;
            while ((data = bufferedReader.read()) >= 0) {
                if (!readAscii || isAscii((char) data)) {
                    result.append((char) data);
                }
            }
            return result.toString();
        }
    }

    private static boolean isAscii(char c) {
        return ENCODER.canEncode(c);
    }
}