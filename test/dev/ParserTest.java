package dev;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
		assertTrue(LATIN_WITH_UNICODE.equals(new String(Files.readAllBytes(testFile.toPath()))));
	}

	@Test
	public void testGetContentStringWithoutUnicode() throws IOException {	
		parser.saveContent(LATIN);
		assertTrue(LATIN.equals(new String(Files.readAllBytes(testFile.toPath()))));
	}

	@Test
	public void testGetContentWithoutUnicodeStringWithoutUnicode()
			throws IOException {
		parser.saveContent(LATIN);
		String actual = new String(Files.readAllBytes(testFile.toPath()));
		assertTrue(LATIN.equals(actual));
	}

	@Test
	public void testGetContentWithoutUnicodeStringWithUnicode() throws IOException {
		parser.saveContent(LATIN_WITH_UNICODE);
		String actual = new String(Files.readAllBytes(testFile.toPath())).replaceAll("\\p{Print}", "");
		assertFalse(LATIN_WITH_UNICODE.equals(actual));
	}
	
	@Test
	public void testSaveContentWithoutUnicode() throws IOException {
		parser.saveContent(LATIN);
		assertTrue(LATIN.equals(new String(Files.readAllBytes(testFile.toPath()))));
	}

	@Test
	public void testSaveContentWithUnicode() throws IOException {
		parser.saveContent(LATIN_WITH_UNICODE);
		assertTrue(LATIN_WITH_UNICODE.equals(new String(Files.readAllBytes(testFile.toPath()))));
	}

}
