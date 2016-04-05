import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	private String testOutput;
	
	@Before
	public void setUp() throws IOException{
		testFilename = "asdf.txt";
		testFilenameUnicode = "asdfUnicode.txt";
		testOutput = "output.txt";
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(testFilenameUnicode));
		bw.write("This is some tiny piece of text stuffs.");
		bw.write(0x79);
		bw.write(0x80);
		bw.write(0x81);
		bw.write(0x82);
		bw.write(0x99);
		bw.write(0xAA);
		bw.write(0xBB);
		bw.write(0x101);
		bw.write(0x103);
		bw.close();
		
		parser = new Parser();
	}
	
	@After
	public void after(){
		deleteIfPresent(testFilenameUnicode);
		deleteIfPresent(testOutput);
	}
	
	private void deleteIfPresent(String filename){
		File deletableFile = new File(filename);
		if(deletableFile.exists()){
			deletableFile.delete();
		}
	}
	
	@Test
	public void shouldReadFileIntoString() throws IOException{
		String content = parser.getContent(new File(testFilename));
		
		assertEquals("This is some tiny piece of text stuffs.", content);
	}
	
	@Test
	public void resultsShouldBeEquivalentWhenNoUnicodeContentInFile() throws IOException{
		String content1 = parser.getContent(new File(testFilename));
		String content2 = parser.getContentWithoutUnicode(new File(testFilename));

		assertEquals(content1, content2);
	}
	
	@Test
	public void resultsShouldContainCharactersBeyond80Hex() throws IOException{
		String content = parser.getContent(new File(testFilenameUnicode), false);
		
		assertTrue(content.indexOf(0x79) >= 0);
		assertTrue(content.indexOf(0x80) >= 0);
		assertTrue(content.indexOf(0x81) >= 0);
		assertTrue(content.indexOf(0x99) >= 0);
		assertTrue(content.indexOf(0xAA) >= 0);
		assertTrue(content.indexOf(0xBB) >= 0);
	}
	
	@Test
	public void resultsShouldNotContainCharactersBeyondFFHex() throws IOException{
		String content = parser.getContent(new File(testFilenameUnicode), false);
		
		assertEquals(-1, content.indexOf(0x101));
		assertEquals(-1, content.indexOf(0x103));
	}
	
	@Test
	public void resultsShouldNotContainCharactersBeyondFFHexEvenWhenIncludingUnicode() throws IOException{
		String content = parser.getContent(new File(testFilenameUnicode), true);
		
		assertEquals(-1, content.indexOf(0x101));
		assertEquals(-1, content.indexOf(0x103));
	}
		
	@Test
	public void shouldWriteStringIntoFile() throws IOException{
		File outputFile = new File(testOutput);
		String content = "But if you ask for a rise it's no surprise when they're giving none away.";
		
		parser.saveContent(outputFile, content);
		
		String readContent = parser.getContent(outputFile);
		assertEquals(content, readContent);
	}
	
}
