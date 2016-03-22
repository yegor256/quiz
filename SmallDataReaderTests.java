import org.junit.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class SmallDataReaderTests {
    private final static String UNICODE_STRING = "ĂdË3";
    private final static String ASCII_STRING = "d3";

    @Test
    public void readUnicode() throws IOException {
        String resultValue = new SmallDataReader().readUnicodeContent(new StringReader(UNICODE_STRING));
        assertThat(resultValue, is(equalTo(UNICODE_STRING)));
    }

    @Test
    public void readAscii() throws IOException {
        String resultValue = new SmallDataReader().readAsciiContent(new StringReader(UNICODE_STRING));
        assertThat(resultValue, is(equalTo(ASCII_STRING)));
    }
}