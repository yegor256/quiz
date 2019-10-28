import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public interface Parser {
	public String content() throws IOException;
}

public interface Writer {
	public void save(String content) throws IOException;
}

public final class SimpleFileParser implements Parser {
	private final File file;

	public SimpleFileParser(File file) {
		this.file = file;
	}

	public String content() throws IOException {
		try (FileInputStream inputStream = new FileInputStream(this.file)) {
			StringBuilder output = new StringBuilder();
			int data;
			while ((data = inputStream.read()) > 0) {
				output.append((char) data);
			}
			return output.toString();
		}
	}
}
public final class FileParserWithoutUnicode implements Parser {
	private final SimpleFileParser simpleFileParser;
	private static final int UNICODE_CHAR = 0x80;

	public FileParserWithoutUnicode(SimpleFileParser simpleFileParser) {
		this.simpleFileParser = simpleFileParser;
	}

	public String content() throws IOException {
		StringBuilder output = new StringBuilder();
		String fileContent = simpleFileParser.content();
		for(int i = 0; i < fileContent.length(); i++){
			char symbol = fileContent.charAt(i);
			if((int)symbol < UNICODE_CHAR){
				output.append(symbol);
			}
		}
		return output.toString();
	}
}

public final class FileWriter implements Writer{
	private final File file;
	
	public FileWriter(File file){
		this.file = file;
	}

	@Override
	public void save(String content) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.file)) {
            for (int i = 0; i < content.length(); i++) {
                fileOutputStream.write(content.charAt(i));
            }
        }
	}
}
