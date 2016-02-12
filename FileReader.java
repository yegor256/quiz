import java.io.IOException;

public interface FileReader {

	/**
	 * Get file content in a string format.
	 * @return String content of the file.
	 * @throws java.io.IOException
	 */
	String readContent() throws IOException;

	/**
	 * Get file content without unicode in a string format.
	 * @return String content of the file.
	 * @throws java.io.IOException
	 */
	public String readContentWithoutUnicode() throws IOException;
}
