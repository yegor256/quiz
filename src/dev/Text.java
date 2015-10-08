package dev;

import java.io.IOException;

public interface Text {
	
	String read() throws IOException;
	
	void write(String content) throws IOException;
}
