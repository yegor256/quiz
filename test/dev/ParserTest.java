package dev;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class ParserTest {
	private Parser parser;
	private File testFile;
	private final static String BASIC_LATIN = "JAVA is the best! ";
	private final static String BASIC_LATIN_WITH_UNICODE = "java приходи ко мне в пещеру будем мамонтов пугать.JAVA";

	@Before
	public void setUp() throws Exception {
		parser = new Parser();
		testFile = new File("testfile.tst");
	}

	@Test
	public void testSetFile() {
		parser.setFile(null);
		parser.setFile(testFile);
		assertEquals(testFile, parser.getFile());
	}

	@Test
	public void testSetFileNull() {
		parser.setFile(null);
		assertNull(parser.getFile());
	}

	@Test
	public void testGetFile() {
		parser.setFile(testFile);
		assertEquals(testFile, parser.getFile());
	}

	@Test
	public void testGetFileNull() {
		parser.setFile(null);
		assertNull(parser.getFile());
	}

	@Test
	public void testGetContentStringWithUnicode() throws IOException {
		parser.setFile(testFile);
		parser.saveContent(BASIC_LATIN_WITH_UNICODE);
		assertTrue(BASIC_LATIN_WITH_UNICODE.equals(parser.getContent()));
	}

	@Test
	public void testGetContentStringWithoutUnicode() throws IOException {
		parser.setFile(testFile);
		parser.saveContent(BASIC_LATIN);
		assertTrue(BASIC_LATIN.equals(parser.getContent()));
	}

	@Test
	public void testGetContentWithoutUnicodeStringWithUnicode() throws IOException {
		parser.setFile(testFile);
		parser.saveContent(BASIC_LATIN_WITH_UNICODE);
		assertFalse(BASIC_LATIN_WITH_UNICODE.equals(parser.getContentWithoutUnicode()));
	}

	@Test
	public void testGetContentWithoutUnicodeStringWithoutUnicode() throws IOException {
		parser.setFile(testFile);
		parser.saveContent(BASIC_LATIN);
		assertTrue(BASIC_LATIN.equals(parser.getContentWithoutUnicode()));
	}

	@Test
	public void testSaveContentStringWithUnicode() throws IOException {
		parser.setFile(testFile);
		parser.saveContent(BASIC_LATIN_WITH_UNICODE);
		assertTrue(BASIC_LATIN_WITH_UNICODE.equals(parser.getContent()));
	}

	@Test
	public void testSaveContentStringWithoutUnicode() throws IOException {
		parser.setFile(testFile);
		parser.saveContent(BASIC_LATIN);
		assertTrue(BASIC_LATIN.equals(parser.getContent()));
	}
}
