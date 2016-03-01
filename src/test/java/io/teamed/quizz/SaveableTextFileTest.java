package io.teamed.quizz;

import static com.google.common.base.Charsets.UTF_8;
import static java.nio.file.Files.readAllBytes;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.junit.Test;

public class SaveableTextFileTest extends AbstractTextFileTest {
  private static final String HELLO_WORLD = "Hello World!";

  @Test
  public void shouldWriteFile() throws IOException {
    final File tmp = folder.newFile();
    final WriteableTextFile newText = newText(tmp.toPath(), UTF_8);
    newText.save(HELLO_WORLD);

    final String content = new String(readAllBytes(tmp.toPath()),UTF_8);
    assertEquals(HELLO_WORLD, content);
  }

  @Override
  protected WriteableTextFile newText(final Path path, final Charset charset) {
    return new WriteableTextFile(path, charset);
  }

}
