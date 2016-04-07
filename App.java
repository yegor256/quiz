package test.refactor;

import java.io.File;
import java.io.IOException;

public class App {

	public static void main(String[] args) {
		File f = null;
		try {
			f = new File("test.txt");
			Parser test1 = new ParserContentWithoutUnicodeImpl(f, new ParserContentImpl(f));
			test1.getContent();
			Parser test2 = new ParserContentImpl(f);
			test2.getContent();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
