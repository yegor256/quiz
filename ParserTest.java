import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Eugene Katrechko on 10/4/15.
 */
public class ParserTest {

    @Test
    public void testSaveContent() {
        Parser parser = new Parser();
        File testFile = new File("plain.txt");
        parser.setFile(testFile);
        String str = "Hello world! \nThis is test.";
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
        Parser parser = new Parser();
        File testFile = new File("plain.txt");
        parser.setFile(testFile);
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
        Parser parser = new Parser();
        parser.setFile(new File("plain.txt"));
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
        Parser parser = new Parser();
        parser.setFile(new File("unicode.txt"));
        String unicodeString = "\u00C6\u00D7\u00E8";
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
        Parser parser = new Parser();
        assertNull("expected null if file is not set in Parser", parser.getFile());
        String path = "file.txt";
        File file = new File(path);
        assertTrue("expected 'true' if file name is set in File", path.equals(file.getName()));
        parser.setFile(file);
        assertEquals("expected equals if file is set in Parser", file, parser.getFile());
    }

    @Test
    public void testGetFile() {
        Parser parser = new Parser();
        assertNull("expected null if file is not set in Parser", parser.getFile());
        String path = "file.txt";
        File file = new File(path);
        parser.setFile(file);
        assertNotNull("expected not null if file is set in Parser", parser.getFile());
        assertEquals("expected equals if file is set in Parser", file, parser.getFile());
        parser.setFile(null);
        assertNull("expected null if file is not set in Parser", parser.getFile());
    }

    @Test(expected = IOException.class)
    public void testFileNotFoundException() throws IOException {
        Parser parser = new Parser();
        parser.setFile(new File("nofile.txt"));
        parser.getContentWithoutUnicode();

    }

    @Test(expected = IOException.class)
    public void testIOExceptionWhenGetContent() throws IOException {
        Parser parser = new Parser();
        parser.setFile(new File("nofile.txt"));
        parser.getContentWithoutUnicode();
    }
}
