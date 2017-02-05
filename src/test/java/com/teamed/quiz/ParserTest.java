package com.teamed.quiz;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest {

	private static final String EXPECTED_CONTENT = "Trabajo mañana en Teamed!";
	private static final String EXPECTED_NON_UNICODE_CONTENT = "Trabajo manana en Teamed!";

	@Test
	public void getContentTest() throws IOException {
		Assert.assertEquals(EXPECTED_CONTENT, new Parser(new File(
				"src/test/resources/example.txt")).getContent());
	}

	@Test
	public void getContentNonUnicodeTest() throws IOException {
		Assert.assertEquals(EXPECTED_NON_UNICODE_CONTENT, new Parser(new File(
				"src/test/resources/example.txt"))
				.getContentWithoutUnicode());
	}

	@Test
	public void saveContentTest() throws IOException {
		File target = new File("target/test.txt");
		new Parser(target).saveContent(EXPECTED_CONTENT);
		Assert.assertEquals(EXPECTED_CONTENT,
				FileUtils.readFileToString(target));
	}
}
