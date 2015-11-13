import java.io.File;
import java.io.IOException;

public class ParserTest {

	public static void main(String[] args) throws Exception {
		test_getContentMustReturnFullContent();
		test_getContentWithouUnicodeMustReturnSimplerContent();
		test_ChekingContentAfterSavingMustBeTheSame();
	}

	public static void test_getContentMustReturnFullContent() {
		Parser parser = new Parser();
		parser.setFile(new File("TestIfFileExistsAndHasContent.txt"));
		try {
			Assert.isTrue(parser.getContent().equals("I have a ç unicode character"));
		} catch (IOException e) {
			throw new TestException("Exception not welcome here!", e);
		}
	}

	public static void test_getContentWithouUnicodeMustReturnSimplerContent() {
		Parser parser = new Parser();
		parser.setFile(new File("TestIfFileExistsAndHasContent.txt"));
		try {
			Assert.isTrue(parser.getContentWithoutUnicode().equals("I have a  unicode character"));
		} catch (IOException e) {
			throw new TestException("Exception not welcome here!", e);
		}
	}

	public static void test_ChekingContentAfterSavingMustBeTheSame() {
		Parser source = new Parser();
		Parser target = new Parser();
		source.setFile(new File("TestIfFileExistsAndHasContent.txt"));
		target.setFile(new File("TestIfFileExistsAndHasContent2.txt"));
		try {
			target.saveContent(source.getContentWithoutUnicode());
			Assert.isTrue(source.getContentWithoutUnicode().equals(target.getContent()));
		} catch (IOException e) {
			throw new TestException("Exception not welcome here!", e);
		}
	}

	public static class TestException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public TestException(String message, Throwable cause) {
			super(message, cause);
		}

		public TestException(String message) {
			super(message);
		}
	}

	public static final class Assert {
		private Assert() {
		}

		public static void isTrue(boolean isTrue) {
			if (!isTrue)
				throw new TestException("Must be true");
		}
	}

}
