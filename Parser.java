
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

	private final File file;

	public Parser(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public String getContentWithoutUnicode() throws IOException {
		return getContent(new UnicodeFilteredContentProcessor());
	}
	
	public String getContent() throws IOException {
		return getContent(new DefaultContentProcessor());
	}

	public String getContent(ContentProcessor contentProcessor) throws IOException {
		if (contentProcessor == null) {
			throw new IllegalArgumentException("no content processor");
		}
		FileInputStream i = new FileInputStream(file);
		int data;
		try {
			while ((data = i.read()) != -1) {
				contentProcessor.process(data);
			}
		} finally {
			i.close();
		}
		return contentProcessor.getContent();
	}

	public void saveContent(String content) throws IOException {
		if (content == null) {
			throw new IllegalArgumentException("no content");
		}
		FileOutputStream os = new FileOutputStream(file);
		try {
			os.write(content.getBytes());
			os.flush();
		} finally {
			os.close();
		}
	}

	public static interface ContentProcessor {

		void process(int data);

		String getContent();
	}

	static class DefaultContentProcessor implements ContentProcessor {

		private StringBuilder contentBuilder;

		public DefaultContentProcessor() {
			contentBuilder = new StringBuilder();
		}

		@Override
		public void process(int data) {
			contentBuilder.append(data);
		}

		@Override
		public String getContent() {
			return contentBuilder.toString();
		}

	}

	static class UnicodeFilteredContentProcessor extends DefaultContentProcessor {

		@Override
		public void process(int data) {
			if (data < 0x80) {
				super.process(data);
			}
		}
	}
}
