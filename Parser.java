import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

	public String getContent(File file) throws IOException {
		StringBuilder output = new StringBuilder();
		BufferedReader buffReader = new BufferedReader(new FileReader(file));
		char[] buffer = new char[1024];
		int len = 0;
		try {
			while((len = buffReader.read(buffer)) != -1){
				output.append(buffer, 0, len);
			}
			return output.toString();
		}
		finally {
			buffReader.close();
		}
	}
	public String getContentWithoutUnicode(File file) throws IOException {
		final FileInputStream i = new FileInputStream(file);
		try {
			StringBuilder output = new StringBuilder();
			int data;
			while ((data = i.read()) != -1) {
				if (data < 0x80) {
					output.append((char) data);
				}
			}
			return output.toString();
		}
		finally {
			i.close();
		}
	}
	public void saveContent(String content, File file) throws IOException {

		final FileOutputStream o = new FileOutputStream(file);

		try {
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		}
		finally {
			o.close();
		}
	}
}
