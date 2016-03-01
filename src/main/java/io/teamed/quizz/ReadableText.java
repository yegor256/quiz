package io.teamed.quizz;

/**
 * Reads text from a given source, 
 * which depends on the implementation.
 * 
 * @author jerome
 *
 */
@FunctionalInterface
public interface ReadableText {

  /**
   * Reads the text content.
   * 
   * @return text content
   * @throws RuntimeException if an error occured while reading
   */
  String read();
}