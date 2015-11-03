import java.io.IOException;

/**
 * Parsers data from a mandatory source.
 */
public interface Parser {
	/**
	 * Returns the parsed data.
	 *
 	 * @return string representation of the data.
	 *
	 * @throws IOException if failed to read the data from the source.
	 */
	String getContent() throws IOException;

	/**
	 * Writes string representation of the data into a mandatory destination.
	 *
	 * @param content - string to write.
	 *
	 * @throws IOException if failed to write data to the destination.
	 */
	void saveContent(String content) throws IOException;
}
