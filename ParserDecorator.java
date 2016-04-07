package test.refactor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public abstract class ParserDecorator implements Parser {
	
	private final File file;
	private Parser parser;
	
	public File getFile() {
		return this.parser.getFile();
	}

	public ParserDecorator(File file, Parser parser) {
		this.file = file;
		this.parser = parser;
	}

	@Override
	public String getContent() throws IOException {
		return this.parser.getContent();
	}

	@Override
	public void saveContent(String content) throws IOException {
		this.parser.saveContent(content);
	}
}
