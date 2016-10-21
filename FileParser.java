import java.io.File;
import java.io.IOException;

/**
 * File parser.
 */
public interface FileParser {
  /**
   * Return current file.
   * @return Current file
   */
  File file();

  /**
   * Return content.
   * @return Content
   * @throws IOException if something is wrong
   */
  String content() throws IOException;

  /**
   * Save content.
   * @param Content to save
   * @throws IOException if something went wrong
   */
  void saveContent(final String content) throws IOException;
}
