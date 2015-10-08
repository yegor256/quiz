package dev;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class FileContent implements Content {
	private final File file;
	private final Charset charset;

	public FileContent(final File file) {
		this(file, Charset.defaultCharset());
	}

	public FileContent(final File file, final Charset charset) {
		this.file = file;
		this.charset = charset;
	}

	@Override
	public String get() throws IOException {
		return new String(Files.readAllBytes(this.file.toPath()), this.charset);
	}

	@Override
	public void set(final String content) throws IOException {
		Files.write(this.file.toPath(), content.getBytes(this.charset));
	}
}
