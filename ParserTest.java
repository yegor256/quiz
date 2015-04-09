import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    private static final String TEST_FILE_CONTENT = "hello world";
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private Parser parser;

    @Before
    public void setUp() throws Exception {
        parser = new Parser(folder.newFile("test.txt"));
    }

    @Test
    public void should_get_content_correct() throws Exception {
        parser.saveContent(TEST_FILE_CONTENT);
        String content = parser.getContent();
        assertEquals(content, TEST_FILE_CONTENT);
    }

    @Test
    public void should_get_content_without_unicode_correct() throws Exception {
        parser.saveContent("hello中国");
        String content = parser.getContentWithoutUnicode();
        assertEquals(content, "hello-");
    }

}