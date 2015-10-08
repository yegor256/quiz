import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Eugene Katrechko on 10/4/15.
 */
public class ParserTest {
    private Parser parser;
    private File testFile;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        parser = new Parser();
        testFile = folder.newFile("testfile.txt");
        parser.setFile(testFile);
    }

    @Test
    public void testSaveContent() {
        String str = "Hello world!\nThis is test.";
        try {
            parser.saveContent(str);
            assertTrue("expected 'true' if file exists", testFile.exists());
            assertTrue("expected 'true' if file is file", testFile.isFile());
            assertTrue("expected 'true' if file can read", testFile.canRead());
            assertTrue("expected 'true' if file can write", testFile.canWrite());
        } catch (IOException e) {
            fail("some I/O exception in saveContent()");
        }
    }

    @Test
    public void testSaveContentUnicode() {
        String unicodeString = "\u00C6\u00D7\u00E8";
        try {
            parser.saveContent(unicodeString);
            assertTrue("expected 'true' if file exists", testFile.exists());
            assertTrue("expected 'true' if file is file", testFile.isFile());
            assertTrue("expected 'true' if file can read", testFile.canRead());
            assertTrue("expected 'true' if file can write", testFile.canWrite());
        } catch (IOException e) {
            fail("some I/O exception in saveContent() unicode string");
        }
    }

    @Test
    public void testSaveReadContent() {
        String str = "Hello world! \nThis is test.";
        try {
            parser.saveContent(str);
            String strFromFile = parser.getContentWithoutUnicode();
            assertTrue("expected 'true' that equal plain strings written and read", str.equals(strFromFile));
        } catch (IOException e) {
            fail("some I/O exception in saveContent() or getContent()");
        }
    }

    @Test
    public void testSaveReadContentUnicode() {
        String unicodeString = "\u00C6\u00D7\u00E8\n\u00C6\u00D7\u00E8\nHello world!\n";
        try {
            parser.saveContent(unicodeString);
            String unicodeStringFromFile = parser.getContent();
            assertTrue("expected 'true' that equal unicode strings written and read", unicodeString.equals(unicodeStringFromFile));
        } catch (IOException e) {
            fail("some I/O exception in saveContent() or getContent()");
        }
    }

    @Test
    public void testSetFile() {
        parser.setFile(null);
        assertNull("expected 'null'", parser.getFile());
        parser.setFile(testFile);
        assertEquals("expected 'equals'",testFile, parser.getFile());
    }

    @Test
    public void testGetFile() {
        parser.setFile(testFile);
        assertEquals("expected 'equals'", testFile, parser.getFile());
    }

    @Test
    public void testSetFileNull() {
        parser.setFile(null);
        assertNull("expected 'null'", parser.getFile());
    }

    @Test
    public void testGetFileNull() {
        parser.setFile(null);
        assertNull("expected 'null'", parser.getFile());
    }

    @Test(expected = Exception.class)
    public void testIOExceptionWhenGetContent() throws Exception {
        parser.setFile(null);
        parser.getContentWithoutUnicode();
    }
}
