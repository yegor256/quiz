import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * I have added this file here, because there are no folders, and packages structure.
 * This test fails before my refactoring commits, because the created file will contain Unicode characters.
 *
 * @author tiborkovacs
 */
public class ParserTest {

	private File tmpFile;
	private String tmpFileContent;

	@Before
	public void setup() throws IOException {
		StringBuilder sb = new StringBuilder(5);
		sb.append(RandomStringUtils.random(10));
		sb.append("\n");
		sb.append(RandomStringUtils.randomAlphabetic(10));
		sb.append("\n");
		sb.append(RandomStringUtils.randomNumeric(10));

		tmpFileContent = sb.toString();

		tmpFile = File.createTempFile("ParserTmpFile", "tmp");

		FileOutputStream fos = new FileOutputStream(tmpFile);

		OutputStreamWriter osw = new OutputStreamWriter(fos, Charset.forName("UTF8"));

		Writer writer = new BufferedWriter(osw);

		try {
			writer.write(tmpFileContent);

			writer.flush();
		}
		finally {
			writer.close();
		}
	}

	@After
	public void tearDown() {
		tmpFile.delete();
	}

	@Test
	public void testGetContent() throws IOException {
		Parser parser = new Parser();

		parser.setFile(tmpFile);

		assertEquals(tmpFileContent, parser.getContent());
	}

	@Test
	public void testGetContentWithoutUnicode() throws IOException {
		Parser parser = new Parser();

		parser.setFile(tmpFile);

		assertNotEquals(tmpFileContent, parser.getContentWithoutUnicode());
	}

	@Test
	public void testSaveContent() throws IOException {
		File saveFile = File.createTempFile("ParserTmpFile2", "tmp");

		Parser parser = new Parser();

		parser.setFile(saveFile);
		parser.saveContent(tmpFileContent);

		FileInputStream fis = new FileInputStream(saveFile);

		InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));

		BufferedReader bufferedReader = new BufferedReader(isr);

		StringBuffer stringBuffer = new StringBuffer((int) saveFile.length());

		try {
			int data;
			while ((data = bufferedReader.read()) > 0) {
				stringBuffer.append((char) data);
			}
		}
		finally {
			bufferedReader.close();
		}

		assertEquals(tmpFileContent, stringBuffer.toString());

		saveFile.delete();
	}

}