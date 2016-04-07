package test.refactor;

import java.io.File;
import java.io.IOException;

public interface Parser {
	public File getFile();
	public String getContent() throws IOException;
	public void saveContent(String content) throws IOException;
}
