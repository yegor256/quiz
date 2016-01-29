import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Changed class name because this class object alone cannot handle parsing all
 * types of files. If made to parse file of all types, code will be too verbose
 * (really bad code and program). So lets assume its a text file parser only.
 * Moreover we can use Parser class as an abstract class inherited by file type
 * specific classes like TextFileParser with their own read/write implementation
 * specific to file types. Replicates real world to a program.
 * 
 * This class was neither thread safe before nor now. Need to come up with file
 * read/write plan like read write locking, single thread for file writing which
 * is out of the scope of refactoring.
 */
public class TextFileParser {

	private File file;

	public synchronized void setFile(File f) {
		file = f;
	}

	public synchronized File getFile() {
		return file;
	}

	/**
	 * Algorithm refactored for more expressive codes.
	 */
	public String getContent() throws IOException {
		
		// Reading file as bytes.
		FileInputStream inputStream = new FileInputStream(file);

		// Bridging for easy conversion from byte to character streams with
		// required encoding.
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "UTF-8");

		// Chaining character stream to buffered reader for the efficient
		// reading of characters, arrays, and lines.
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		// Using string builder instead of string concatenation for optimized
		// performance.
		StringBuilder output = new StringBuilder();
		String line;

		while ((line = bufferedReader.readLine()) != null) {
			output.append(line);
		}

		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();

		return output.toString();
	}

	/**
	 * Algorithm refactored for more expressive codes.
	 */
	public String getContentWithoutUnicode() throws IOException {
		
		// Reading file as bytes.
		FileInputStream inputStream = new FileInputStream(file);

		// Bridging for easy conversion from byte to character streams with
		// required encoding.
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "UTF-8");

		// Using string builder instead of string concatenation for optimized
		// performance.
		StringBuilder output = new StringBuilder();
		int data;
		while ((data = inputStreamReader.read()) > 0) {
			if (data < 0x80) {
				output.append(data);
			}
		}

		inputStreamReader.close();
		inputStream.close();

		return output.toString();
	}

	/**
	 * Algorithm refactored for more expressive codes.
	 */
	public void saveContent(String content) throws IOException {
		
		// Writing file as bytes.
		FileOutputStream outputStream = new FileOutputStream(file);

		// Bridging for easy conversion from character to byte streams with
		// required encoding.
		OutputStreamWriter outputStreamWrither = new OutputStreamWriter(
				outputStream);

		// Chaining character stream writer to buffered writer for the efficient
		// writing of characters, arrays, and lines.
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWrither);

		bufferedWriter.write(content);

		bufferedWriter.close();
		outputStreamWrither.close();
		outputStream.close();
	}
}
