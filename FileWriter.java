import java.io.IOException;

public interface FileWriter {

	/**
	 * Write content to the end of the file.
	 * @param content content to wright
	 * @throws java.io.IOException
	 */
	public void appendContentToFile(String content) throws IOException;
}
