import java.io.File;
import java.util.Optional;

/**
 * Reads data from a UTF-8 encoded file and skips every
 * integer that cannot be converted to its ASCII equivalent.
 */
public class FilteringParser extends DefaultParser {
	private static final int MAX_ASCII_CHAR_COUNT = 128;

	public FilteringParser(final File file) {
		super(file);
	}

	@Override
	protected Optional<Character> parse(final int data) {
		Optional<Character> ch = Optional.empty();
		if (data < FilteringParser.MAX_ASCII_CHAR_COUNT) {
			ch = Optional.of((char) data);
		}
		return ch;
	}
}
