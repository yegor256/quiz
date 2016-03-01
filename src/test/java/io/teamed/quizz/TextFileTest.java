package io.teamed.quizz;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import com.google.common.base.Charsets;

public class TextFileTest extends AbstractTextFileTest {
  private static final String HELLO_WORLD = "Hello World!";

  @Test
  public void shouldReadFile() throws IOException {
    final File tmp = folder.newFile();
    Files.write(tmp.toPath(), HELLO_WORLD.getBytes(Charsets.UTF_8));
    final ReadableTextFile newText = newText(tmp.toPath(), Charsets.UTF_8);
    assertEquals(HELLO_WORLD, newText.read());
  }

  @Override
  protected ReadableTextFile newText(final Path path, final Charset charset) {
    return new ReadableTextFile(path, charset);
  }

}
