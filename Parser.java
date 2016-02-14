package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

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

	// Reading a unicode file
	public String getContent() throws IOException {
		InputStream fileInputStream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(fileInputStream,
				Charset.forName("UTF-8"));
		BufferedReader brReader = new BufferedReader(reader);
		String output = "";
		String data;
		while ((data = brReader.readLine()) != null) {
			output += data;
		}
		brReader.close();
		reader.close();
		fileInputStream.close();
		return output;
	}

	// Reading a non-unicode file

	public String getContentWithoutUnicode() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));

		String output = "";
		String line;
		while ((line = reader.readLine()) != null) {

			output += line;
		}
		reader.close();
		return output;
	}

	// Saving the file
	public void saveContent(String content) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(content);
		writer.close();
	}
}
