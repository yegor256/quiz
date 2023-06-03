package dev;

import java.io.IOException;

public class NonUnicodeContent implements Content {
	private final Content origin;

	public NonUnicodeContent(final Content origin) {
		this.origin = origin;
	}

	@Override
	public String get() throws IOException {
		return this.origin.get().replaceAll("\\P{Print}", "");
	}

	@Override
	public void set(final String content) throws IOException {
		this.origin.set(content);
	}
}
