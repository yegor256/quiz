package com.teamed.quiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Normalizer;

/**
 * This class is thread safe.
 */
public class Parser {

	private static final int BUFFER_SIZE = 100;

	private final File file;

	public Parser(File file) {
		this.file = file;
	}

	public String getContent() throws IOException {
		// First of all, I'd would use Apache IOUtils FileUtils.readFileToString
		// or something
		// like that...
		// Wouldn't do this myself
		try (FileInputStream i = new FileInputStream(file)) {

			// Use StringBuilder instead of concatenating strings
			StringBuilder b = new StringBuilder();

			// Use a buffer to improve efficiency, tune buffer size for better
			// perfomance (do not use StringBuilder if performance is really
			// important, use a big enough buffer in that case)
			byte[] buffer = new byte[BUFFER_SIZE];
			int read = i.read(buffer);
			while (read > 0) {
				b.append(new String(buffer, 0, read));
				read = i.read(buffer);
			}
			return b.toString();
		}
	}

	public String getContentWithoutUnicode() throws IOException {
		String normalized = Normalizer.normalize(getContent(),
				Normalizer.Form.NFD);
		return normalized.replaceAll("[^\\x00-\\x7F]", "");

	}

	public void saveContent(String content) throws IOException {
		try (FileOutputStream o = new FileOutputStream(file)) {
			o.write(content.getBytes());
		}
	}
}
