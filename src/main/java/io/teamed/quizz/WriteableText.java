package io.teamed.quizz;

/**
 * Saves a given text as string to a location which depends 
 * on the implementation. 
 * 
 * @author jerome
 *
 */
@FunctionalInterface
public interface WriteableText {

  /**
   * @param content text to save
   * @throws RuntimeException when could not be saved
   */
  void save(String content);
}
