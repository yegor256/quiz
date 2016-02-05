import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Parser {

	private final File file;
	private final ReentrantReadWriteLock lock;

	public Parser(final File file) {
		validate(file);

		this.file = file;
		this.lock = new ReentrantReadWriteLock();
	}

	public String getContent(final boolean withUnicode) throws IOException {
		lock.writeLock().lock();

		try (final FileInputStream i = new FileInputStream(file)) {
			final StringBuilder output = new StringBuilder();
			int data;
			while ((data = i.read()) > 0) {
				if (withUnicode || data < 0x80) {
					output.append((char) data);
				}
			}

			return output.toString();
		} finally {
			lock.writeLock().unlock();
		}
	}

	public String getContentWithoutUnicode() throws IOException {
		return getContent(false);
	}

	public void saveContent(final File file, final String content) throws IOException {
		lock.readLock().lock();

		try (final FileOutputStream o = new FileOutputStream(file)) {
			o.write(content.getBytes());
		} finally {
			lock.readLock().unlock();
		}
	}

	private void validate(final File file) {
		if (file == null) {
			throw new NullPointerException("File cannot be null");
		}
		if (!file.exists()) {
			throw new IllegalArgumentException("File not exists");
		}
		if (file.isDirectory()) {
			throw new IllegalArgumentException("File should be file not directory");
		}
	}
}
