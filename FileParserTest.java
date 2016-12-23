import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;

public class FileParserTest {
    private static final String TEST_FILE_CONTENT = "hello world";
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private FileParser fileParser;

    @Before
    public void setUp() throws Exception {
        fileParser = new FileParser(folder.newFile("test.txt"));
    }

    @Test
    public void should_get_content_correct() throws Exception {
        fileParser.saveContent(TEST_FILE_CONTENT);
        String content = fileParser.getContent();
        assertEquals(content, TEST_FILE_CONTENT);
    }

    @Test
    public void should_get_content_without_unicode_correct() throws Exception {
        fileParser.saveContent("hello中国");
        String content = fileParser.getContentWithoutUnicode();
        assertEquals(content, "hello-");
    }

}