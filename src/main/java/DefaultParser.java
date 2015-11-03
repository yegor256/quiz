import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

/**
 * Reads data from a Java UTF-8 encoded file into a string.
 */
public class DefaultParser implements Parser {
	private final File file;

	public DefaultParser(final File file) {
		this.file = file;
	}

	protected File getFile() {
		return this.file;
	}

	@Override
	public String getContent() throws IOException {
		String[] output = {""};

		try (
			InputStream i = this.getInputStream(this.getFile());
		) {
			int data;
			while ((data = i.read()) >= 0) {
				this.parse(data).ifPresent(ch -> output[0]+= ch);
			}
		}

		return output[0];
	}

	@Override
	public void saveContent(final String content) throws IOException {
		try (
			OutputStream o = this.getOutputStream(this.getFile());
		) {
			for (int i = 0; i < content.length(); i++) {
				o.write(content.charAt(i));
			}
		}
	}

	/**
	 * Parses an integer. By default every integer is considered to be
	 * a Java UTF-8 encoded character.
	 *
	 * @param data - integer to parse.
	 * @return A character representation of the integer.
	 *         If the integer cannot be represented then a empty optional is returned.
	 */
	protected Optional<Character> parse(final int data) {
		Optional<Character> result = Optional.of((char) data);
		return result;
	}

	/**
	 * Needed only for testing.
	 */
	InputStream getInputStream(final File file) throws FileNotFoundException {
		FileInputStream stream = new FileInputStream(file);
		return stream;
	}

	/**
	 * Needed only for testing.
	 */
	OutputStream getOutputStream(final File file) throws FileNotFoundException {
		FileOutputStream stream = new FileOutputStream(file);
		return stream;
	}
}
