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

	public synchronized void setFile(File f) {
		file = f;
	}

	public synchronized File getFile() {
		return file;
	}

	public String getContent() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder output = new StringBuilder();
			int data;
			while ((data = reader.read()) != -1) {
				output.append((char) data);
			}
			return output.toString();
		}
	}

	public String getContentWithoutUnicode() throws IOException {
				return getContent().replaceAll("\\P{Print}", "");
	}

	public void saveContent(String content) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(content);
		}
	}
}
