package dev;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFile implements Text {
	private final File file;
	
	public TextFile(File file) {
			this.file = file;
	}

	@Override
	public String read() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder output = new StringBuilder();
			int data;
			while ((data = reader.read()) != -1) {
				output.append((char) data);
			}
			return output.toString();
		}
	}

	@Override
	public void write(String content) throws IOException {		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(content);
		}
		
	}

}
