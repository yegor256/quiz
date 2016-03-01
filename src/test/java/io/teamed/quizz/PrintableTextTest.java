package io.teamed.quizz;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.testing.NullPointerTester;

public class PrintableTextTest {
  private static final String HELLO_WORLD = "Hello World!\n";

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().testConstructors(PrintableText.class,PACKAGE);
  }

  @Test
  public void shouldReturnPrintable() {
    final ReadableText readable = () -> HELLO_WORLD;
    final ReadableText printable = new PrintableText(readable);
    assertEquals("Hello World!", printable.read());
  }
}
