import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParserTest {

	private Parser parser;
	
	private String testFilename;
	private String testFilenameUnicode;
	
	@Before
	public void setUp() throws IOException{
		testFilename = "asdf.txt";
		testFilenameUnicode = "asdfUnicode.txt";
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(testFilenameUnicode));
		bw.write("This is some tiny piece of text stuffs.");
		bw.write(0x101);
		bw.write(0x102);
		bw.close();
		
		parser = new Parser();
	}
	
	@After
	public void after(){
		File unicodeFile = new File(testFilenameUnicode);
		if(unicodeFile.exists()){
			unicodeFile.delete();
		}
	}
	
	@Test
	public void shouldReadFileIntoString() throws IOException{
		parser.setFile(new File(testFilename));
		String content = parser.getContent();
		
		assertEquals("This is some tiny piece of text stuffs.", content);
	}
	
	@Test
	public void resultsShouldBeEquivalentWhenNoUnicodeContentInFile() throws IOException{
		parser.setFile(new File(testFilename));
		String content1 = parser.getContent();
		String content2 = parser.getContentWithoutUnicode();

		assertEquals(content1, content2);
	}
	
	@Test
	public void resultsShouldNotBeEquivalentWhenUnicodeContentIsFound() throws IOException{
		parser.setFile(new File(testFilenameUnicode));
		String content1 = parser.getContent();
		String content2 = parser.getContentWithoutUnicode();

		assertNotEquals(content1, content2);
	}
	
}
