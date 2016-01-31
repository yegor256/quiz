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
 */
public class TextFileParser {

	private File file;

	/**
	 * Assuming file parsers object would not be singleton. Parameterized
	 * constructor to make file parsers object immutable. This will make the
	 * file parsers thread safe not allowing to change the file by other thread
	 * and also prevents NPE.
	 */
	public TextFileParser(File file) {
		this.file = file;
	}

	public File getFile() {
		return this.file;
	}

	/**
	 * Algorithm refactored for more expressive codes.
	 */
	public String getContent() throws IOException {

		// Local variable must be initialized.
		FileInputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		StringBuilder output = new StringBuilder();

		try {

			// Reading file as bytes.
			inputStream = new FileInputStream(this.file);

			// Bridging for easy conversion from byte to character streams with
			// required encoding.
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

			// Chaining character stream to buffered reader for the efficient
			// reading of characters, arrays, and lines.
			bufferedReader = new BufferedReader(inputStreamReader);

			// Using string builder instead of string concatenation for
			// optimized
			// performance.
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				output.append(line);
			}
		} catch (IOException e) {

			// Logging needed.
			// It would be good to handle exception in business layer.
			// So that appropriate response could be built based on the
			// exceptions caught.
			throw e;
		} finally {

			if (bufferedReader != null) {
				bufferedReader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
		}

		return output.toString();
	}

	/**
	 * Algorithm refactored for more expressive codes.
	 */
	public String getContentWithoutUnicode() throws IOException {

		// Local variable must be initialized.
		FileInputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		StringBuilder output = new StringBuilder();

		try {

			// Reading file as bytes.
			inputStream = new FileInputStream(this.file);

			// Bridging for easy conversion from byte to character streams with
			// required encoding.
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

			// Using string builder instead of string concatenation for
			// optimized
			// performance.
			int data;
			while ((data = inputStreamReader.read()) > 0) {
				if (data < 0x80) {
					output.append((char) data);
				}
			}
		} catch (IOException e) {

			// Logging needed.
			// It would be good to handle exception in business layer.
			// So that appropriate response could be built based on the
			// exceptions caught.
			throw e;
		} finally {

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
		}

		return output.toString();
	}

	/**
	 * Algorithm refactored for more expressive codes.
	 */
	public void saveContent(String content) throws IOException {

		// Local variable must be initialized.
		FileOutputStream outputStream = null;
		OutputStreamWriter outputStreamWrither = null;
		BufferedWriter bufferedWriter = null;

		try {

			// Writing file as bytes.
			outputStream = new FileOutputStream(this.file);

			// Bridging for easy conversion from character to byte streams with
			// required encoding.
			outputStreamWrither = new OutputStreamWriter(outputStream);

			// Chaining character stream writer to buffered writer for the
			// efficient
			// writing of characters, arrays, and lines.
			bufferedWriter = new BufferedWriter(outputStreamWrither);

			bufferedWriter.write(content);
		} catch (IOException e) {

			// Logging needed.
			// It would be good to handle exception in business layer.
			// So that appropriate response could be built based on the
			// exceptions caught.
			throw e;
		} finally {

			if (bufferedWriter != null) {
				bufferedWriter.close();
			}

			if (outputStreamWrither != null) {
				outputStreamWrither.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}