package dev;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
	private File file;
	Text text;

	public synchronized void setFile(File f) {
		file = f;
		text = new TextFile(file);
	}

	public synchronized File getFile() {
		return file;
	}

	public String getContent() throws IOException {		
			return text.read();		
	}

	public String getContentWithoutUnicode() throws IOException {
				return new NonUnicodeTextFile(text).read();
	}

	public void saveContent(String content) throws IOException {
		text.write(content);
	}
}
