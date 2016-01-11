import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void vanillaSaveAndGetContent() throws IOException {
        File file = new File(generateFileName());
        assertTrue(file.createNewFile());

        Parser parser = new Parser(file);

        parser.saveContent("Hello"+System.lineSeparator()+"Hello");
        assertEquals("Hello"+System.lineSeparator()+"Hello", parser.getContent());
    }

    private String generateFileName() {
        return System.getProperty("java.io.tmpdir") + "/" + System.nanoTime() + ".txt";
    }
}
