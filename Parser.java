import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {

	private AtomicReference<File> file = new AtomicReference<>();

	private ReadWriteLock fileLock = new ReentrantReadWriteLock();

	public void setFile(File f) {
		file.set(f);
	}

	public File getFile() {
		return file.get();
	}

	public String getContent(final boolean onlyAscii) throws IOException {
		try (final FileInputStream i = new FileInputStream(getFile());) {
			fileLock.readLock().lock();
			final StringBuilder stb = new StringBuilder();
			int data;
			while ((data = i.read()) > 0) {
				if (Character.UnicodeBlock.BASIC_LATIN.equals(Character.UnicodeBlock.of((char) data)) 
						|| onlyAscii) {
					stb.append((char) data);
				}
			}
			return stb.toString();
		} finally {
			fileLock.readLock().unlock();
		}
	}

	public void saveContent(final String content) throws IOException {
		try (final BufferedWriter outputFile = new BufferedWriter(new FileWriter(getFile()));) {
			fileLock.writeLock().lock();
			outputFile.write(content);
		} finally {
			fileLock.writeLock().unlock();
		}
	}

}
