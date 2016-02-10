import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class ParserWithoutStaticMethod {
	private static final int FIRST_UNICODE_CHARACTER = 0x80;

	private File file;
	private ReadWriteLock rwLock = new ReentrantReadWriteLock();

	public File getFile() {
		Lock l = rwLock.readLock();
		File file = null;
		l.lock();
		try {
			file = this.file;
		} catch (Exception ex) {
			//...
		} finally {
			l.unlock();
		}
		return file;
	}

	public void setFile(File file) {
		Lock l = rwLock.writeLock();
		l.lock();
		try {
			this.file = file;
		} catch (Exception ex) {
			//...
		} finally {
			l.unlock();
		}
	}

	public String getContent() throws IOException {
		return getStringContent(true);
	}

	public String getContentWithoutUnicode() throws IOException {
		return getStringContent(false);
	}

	public void saveContent(String content) throws IOException {
		Lock l = rwLock.writeLock();
		l.lock();
		try (FileOutputStream o = new FileOutputStream(this.file)) {
			for (int i = 0; i < content.length(); i += 1) {
				o.write(content.charAt(i));
			}
		} catch (Exception ex) {
			//...
		} finally {
			l.unlock();
		}
	}

	private String getStringContent(boolean withUnicode) throws IOException {
		StringBuilder output = new StringBuilder();

		Lock l = rwLock.readLock();
		File file = null;
		l.lock();
		try {
			file = this.file;
			try (FileInputStream i = new FileInputStream(file)) {
				int data;
				while ((data = i.read()) > 0) {
					if (withUnicode) {
						output.append((char) data);
					} else {
						if (data < FIRST_UNICODE_CHARACTER) {
							output.append((char) data);
						}
					}
				}
			}
		} catch (Exception ex) {
			//...
		} finally {
			l.unlock();
		}

		return output.toString();
	}
}
