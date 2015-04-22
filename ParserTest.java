import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * @author bardyshev
 * @since 22.04.2015
 */
public class ParserTest {

    private Parser parser;

    @Before
    public void setUp() throws Exception {

        parser = new Parser();

        // specify real file in classpath
        File testFile = new File(this.getClass().getResource("testfile.txt").toURI());
        parser.setFile(testFile);

    }

    @Test
    public void testGetContent() throws Exception {

        Assert.assertEquals("this is test content \u044E\u043D\u0438\u043A\u043E\u0434", parser.getContent());

    }

    @Test
    public void testGetContentWithoutUnicode() throws Exception {

        Assert.assertEquals("this is test content ", parser.getContentWithoutUnicode());

    }

}
