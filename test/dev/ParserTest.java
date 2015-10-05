package dev;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ParserTest {
	Parser parser;
	File testFile;
	String content;
	String contentWithUnicode;

	@Before
	public void initialize() {
		parser = new Parser();
		testFile = new File("test.txt");
		content = "Hello teamed.io";
		contentWithUnicode = "Hello, teamed.io \u00c8";
	}

	@Test
	public void testSaveContent() {
		parser.setFile(testFile);
		try {
			parser.saveContent(content);
			assertTrue(testFile.exists());
			assertTrue(testFile.isFile());
			assertTrue(testFile.canRead());
			assertTrue(testFile.canWrite());
		} catch (IOException e) {
			fail("I/O Exeption was caught");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetContent() {
		parser.setFile(testFile);
		try {
			parser.saveContent(content);
			assertTrue(content.equals(parser.getContent()));
		} catch (IOException e) {
			fail("I/O Exeption was caught");
			e.printStackTrace();
		}
	}
	@Test
	public void testGetContentWithUnicode() {
		parser.setFile(testFile);
		try {
			parser.saveContent(contentWithUnicode);
			assertTrue(contentWithUnicode.equals(parser.getContent()));
		} catch (IOException e) {
			fail("I/O Exeption was caught");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetContentWithoutUnicode() {
		parser.setFile(testFile);
		String expected = "Hello, teamed.io ";
		try {
			parser.saveContent(contentWithUnicode);			
			assertTrue(expected.equals(parser.getContentWithoutUnicode()));
		} catch (IOException e) {
			fail("I/O Exeption was caught");
			e.printStackTrace();
		}
	}
}
