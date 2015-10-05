package dev;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser extends ContentManager implements FileUtil {
	
	@Override
	public synchronized String getContentWithoutUnicode() throws IOException {
		try (FileInputStream i = new FileInputStream(file)) {
			StringBuilder output = new StringBuilder();
			int data;
			while ((data = i.read()) != -1) {
				if (data < 0x80) {
					output.append((char) data);
				}
			}
			return output.toString();
		}
	}
}
