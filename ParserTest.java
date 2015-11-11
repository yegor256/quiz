import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.*;

public class ParserTest {

    private Parser parser;

    @Before
    public void setUp() throws Exception {
        this.parser = new Parser();
    }

    @Test
    public void getContent() throws Exception {
        assertEquals("The quick brown fox jumps over the lazy dog", parser.getContent(new File("ascii.txt")));
    }

    @Test
    public void getContentWithoutUnicode() throws Exception {
        assertEquals("Alle Trmpfe in der Hand halten", parser.getContentWithoutUnicode(new File("unicode.txt")));
    }

    @Test
    public void saveContent() throws Exception {
        File file = new File("save.txt");
        String content = "some content";
        parser.saveContent(content, file);
        assertEquals(content, new BufferedReader(new FileReader(file)).readLine());
        boolean delete = file.delete();
        if (!delete) {
            fail("Failed to clean up after test!");
        }
    }


}