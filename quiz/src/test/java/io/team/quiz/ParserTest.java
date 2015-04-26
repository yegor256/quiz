package io.team.quiz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gadi
 */
public class ParserTest {

    private Parser instance;

    public ParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        // create the test instance
        instance = new Parser();

        // load a test file
        //ClassLoader classLoader = getClass().getClassLoader();
        //file = new File(classLoader.getResource("test.txt").getFile());
    }

    private File getFile(String content) throws IOException {
        File file = new File("test.txt");
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            output.write(content);
        }
        return file;
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setFile method, of class Parser.
     */
    @org.junit.Test
    public void testSetAndGetFile() throws IOException {
        File file = getFile("Hello Gadi");
        instance.setFile(file);
        assertEquals(file, instance.getFile());
    }

    /**
     * Test of getContent method, of class Parser.
     */
    @org.junit.Test
    public void testGetContentNotNull() throws Exception {
        instance.setFile(getFile("Test"));
        String result = instance.getContent();
        assertNotNull(result);
    }

    @org.junit.Test
    public void testGetContentValue() throws Exception {
        instance.setFile(getFile("Hello World!ä"));
        String result = instance.getContent();
        assertTrue(result.equals("Hello World!ä"));
    }

    /**
     * Test of getContentWithoutUnicode method, of class Parser.
     */
    @org.junit.Test
    public void testGetContentWithoutUnicode() throws IOException {
        instance.setFile(getFile("Hello World!ä"));
        String result = instance.getContentWithoutUnicode();
        assertTrue(result.equals("Hello World!"));
    }

    /**
     * Test of saveContent method, of class Parser.
     */
    @org.junit.Test
    public void testSaveContent() throws IOException {
        instance.setFile(getFile("Somthing"));
        instance.saveContent("Hello");
        assertTrue(instance.getContent().equals("Hello"));
    }

}
