package dev;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ContentManager {

	protected File file;

	public ContentManager() {
		super();
	}

	public synchronized void setFile(File f) {
		file = f;
	}

	public synchronized File getFile() {
		return file;
	}

	public synchronized String getContent() throws IOException {
		try (FileInputStream i = new FileInputStream(file)) {
			StringBuilder output = new StringBuilder();
			int data;
			while ((data = i.read()) != -1) {
				output.append((char) data);
			}
			return output.toString();
		}
	}

	public synchronized void saveContent(String content) throws IOException {
		try (FileOutputStream o = new FileOutputStream(file)) {
			for (int i = 0; i < content.length(); i++) {
				o.write(content.charAt(i));
			}
		}
	}

}