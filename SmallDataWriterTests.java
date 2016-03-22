import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class SmallDataWriterTests {
    private final static String UNICODE_STRING = "ĂdË3";

    @Test
    public void writeUnicode() throws IOException {
        StringWriter writer = new StringWriter();
        new SmallDataWriter().writeContent(writer, UNICODE_STRING);

        assertThat(writer.toString(), is(equalTo(UNICODE_STRING)));
    }
}