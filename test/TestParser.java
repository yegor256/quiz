import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by ekondrashev on 3/21/15.
 */
public class TestParser {

    @Test
    public void testGetContentWitoutUnicode() throws IOException{
        Parser p = new Parser("юникод ascii");
        assertEquals(p.getContentWithoutUnicode(), " ascii");
    }
}
