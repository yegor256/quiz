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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gadi
 */
public class ParserTest {

    private static final Logger logger = LoggerFactory.getLogger(ParserTest.class) ;
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
    }

    private File getFile(String content) throws IOException {
        //create a new file
        File file = new File("test.txt");
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            // load the file with content from input
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
    public void testGetContentNotNull() throws IOException {
        instance.setFile(getFile("Test"));
        String result = instance.getContent();
        assertNotNull(result);
    }

    @org.junit.Test
    public void testGetContentValue() throws Exception {
        instance.setFile(getFile("Hello World!ä"));
        String result = instance.getContent();
        logger.info("Result: {}", result);
        assertTrue(result.equals("Hello World!ä"));
    }

    /**
     * Test of getContentWithoutUnicode method, of class Parser.
     */
    @org.junit.Test
    public void testGetContentWithoutUnicode() throws IOException {
        instance.setFile(getFile("Hello World!ä"));
        String result = instance.getContentWithoutUnicode();
        logger.info("Result: {}", result);        
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
