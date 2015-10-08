package dev;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Kolobov Alexsander
 *
 */
public class ParserTest {
	private Parser parser;
	private File testFile;
	private final String LATIN = "Hello teamed.io";;
	private final String LATIN_WITH_UNICODE = "Hello, ку ку шечка teamed.io ";

	@Before
	public void initialize() throws Exception {
		parser = new Parser();
		testFile = new File("test.txt");
		parser.setFile(testFile);
	}

	
	@Test
	public void testGetContentStringWithUnicode() throws IOException {
	
		parser.saveContent(LATIN_WITH_UNICODE);
		assertTrue(LATIN_WITH_UNICODE.equals(parser.getContent()));
	}

	@Test
	public void testGetContentStringWithoutUnicode() throws IOException {	
		parser.saveContent(LATIN);
		assertTrue(LATIN.equals(parser.getContent()));
	}

	@Test
	public void testGetContentWithUnicodeStringWithoutUnicode()
			throws IOException {
		parser.saveContent(LATIN);
		assertTrue(LATIN.equals(parser.getContent()));
	}

	@Test
	public void testGetContentWithoutUnicodeStringWithUnicode() throws IOException {
		parser.saveContent(LATIN_WITH_UNICODE);
		assertFalse(LATIN_WITH_UNICODE.equals(parser.getContentWithoutUnicode()));
	}
	
	@Test
	public void testSaveContentWithoutUnicode() throws IOException {
		parser.saveContent(LATIN);
		assertTrue(LATIN.equals(parser.getContent()));
	}

	@Test
	public void testSaveContentWithUnicode() throws IOException {
		parser.saveContent(LATIN_WITH_UNICODE);
		assertTrue(LATIN_WITH_UNICODE.equals(parser.getContent()));
	}

}
