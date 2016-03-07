package quiz;

import java.io.File;
import java.io.IOException;


public class ParserTest {

	public static void main(String[] args) throws IOException {
		boolean getContentTest,getContentWithoutUnicodeTest,saveContentTest;
		getContentTest = getContentWithoutUnicodeTest = saveContentTest = false;
		try{
			File dummy = new File("dummyFile");
			String testString = "Hell" + (char) 333  +" World";
			// if this fails, so will getContent
			Parser.saveContent(testString,dummy);
			if(Parser.getContent(dummy).equals(testString)){
				saveContentTest = true;
			}		
			String content = Parser.getContent(dummy);
			if (content.equals(testString)){
				getContentTest = true;
			}
			content = null;
			content = Parser.getContentWithoutUnicode(dummy);
			if (content.equals("Hell World")){
				getContentWithoutUnicodeTest = true;
			}
			dummy.delete();
		}

		finally{
			System.out.println("getContentTest " + getContentTest );
			System.out.println("saveContentTestt " + saveContentTest );
			System.out.println("getContentWithoutUnicodeTest " + getContentWithoutUnicodeTest);
		}
		
	}

}
