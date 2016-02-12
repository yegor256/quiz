import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.sun.istack.internal.NotNull;

/**
 * This class is thread safe and immutable.
 */
public class BlockFreeParser extends Parser implements FileReader, FileWriter {

	private final File file;
	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * Creates a new parser instance. All operation on file inside of this class a thread safe and block free.
	 * @param file file that you probably want to parse.
	 */
	public BlockFreeParser(@NotNull File file) {
		this.file = file;
	}

	/**
	 * Get file content in a string format.
	 * @return String content of the file.
	 * @throws IOException
	 */
	@Override
	public String readContent() throws IOException {
		try {
			lock.readLock().lock();
			return getContent();
		}
		finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * Get file content without unicode in a string format.
	 * @return String content of the file.
	 * @throws IOException
	 */
	@Override
	public String readContentWithoutUnicode() throws IOException {
		try {
			lock.readLock().lock();
			return getContentWithoutUnicode();
		}
		finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * Write content to the end of the file.
	 * @param content content to wright
	 * @throws java.io.IOException
	 */
	@Override
	public void appendContentToFile(String content) throws IOException {
		try {
			lock.writeLock().lock();
			saveContent(content);
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	public File getFile() {
		return file;
	}

	/**
	 * Set operation is unsupported. BlockFreeParser is immutable class.
	 */
	public void setFile(File file) {
		throw new UnsupportedOperationException();
	}
}