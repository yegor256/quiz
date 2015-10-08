package dev;

import java.io.IOException;

public interface Content {
	String get() throws IOException;

	void set(String content) throws IOException;
}
