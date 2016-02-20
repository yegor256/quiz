import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
	private File file;
	
	private synchronized void setFile(File f) {
		file = f;
	}

	private synchronized File getFile() {
		return file;
	}

	// with the use of this no need to convert int to char.
	// should be close buffer in finally.
	// it can be compile faster with use of readLine() method.
	private String getContent() {
		BufferedReader br = null;
		String output = "";
		try {

			br = new BufferedReader(new FileReader(file));

			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return output;
	}

	private String getContentWithoutUnicode() throws IOException {
		FileInputStream i = new FileInputStream(file);
		String output = "";
		int data;
		while ((data = i.read()) > 0) {
			if (data < 0x80) {
				output += (char) data;
			}
		}

		return output;
	}

	private void saveContent(String content) throws IOException {

		// if file doesn't exists, then create it.
		// with the use of this there is no need to run loop with it length.

		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();

		System.out.println("Done");

	}

}
