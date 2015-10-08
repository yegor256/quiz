package dev;

import java.io.IOException;

public class NonUnicodeTextFile implements Text {
private final Text text;

	public NonUnicodeTextFile(Text text) {
	
	this.text = text;
}

	@Override
	public String read() throws IOException {
		this.text.read().replaceAll("\\P{Print}", "");
		return null;
	}

	@Override
	public void write(String content) throws IOException {
		this.text.write(content);
		
	}

}
