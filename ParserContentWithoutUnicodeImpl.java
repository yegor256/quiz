package test.refactor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ParserContentWithoutUnicodeImpl extends ParserDecorator {

	private final File file;
	private Parser parser;
	
	public ParserContentWithoutUnicodeImpl(File file, Parser parser) {
		super(file, parser);
		this.file = file;
		this.parser = parser;
	}

	@Override
	public String getContent() throws IOException {
		InputStream i = null;
		StringBuilder output = null;
		try {
			i = new FileInputStream(file);
			output = new StringBuilder();
			int data;
			while ((data = i.read()) > 0) {
				output.append((char) data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			i.close();
		}
		
		return output.toString();
	}

	@Override
	public void saveContent(String content) throws IOException {
		this.parser.saveContent(content);
	}

}
