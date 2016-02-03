import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

public class FileContentTest {
    FileContent fileContent = new FileContent();

    @Before
    public void setup(){
        fileContent.setFile(new File("C:/temp/TestFile.txt"));
    }

    @Test
    public void testContent() throws Exception {
        fileContent.saveContent("Refactoring starts with testing!");

        Assert.assertEquals(fileContent.getContent(),"Refactoring starts with testing!");
    }

    @Test
    public void testContentWithoutUnicode() throws Exception {
        fileContent.saveContent("Refactoring starts with tĔsting!");

        Assert.assertEquals(fileContent.getContentWithoutUnicode(),"Refactoring starts with t\u0014sting!");
    }
}