package io.teamed.quizz;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.testing.NullPointerTester;

public abstract class AbstractTextFileTest {
  @Rule
  public final TemporaryFolder folder = new TemporaryFolder();

  @Test(expected=IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentException() throws IOException {
    newText(folder.newFolder().toPath(), UTF_8);
  }

  @Test
  public void shouldCreateNewWithRegularFile() throws IOException {
    final Path tmp = folder.newFile().toPath();
    final AbstractTextFile text = newText(tmp, UTF_8);
    assertNotNull(text);
  }

  @Test
  public void shouldPassNPETester() throws IOException {
    final Path tmp = folder.newFile().toPath();
    final AbstractTextFile text = newText(tmp, UTF_8);
    new NullPointerTester().testConstructors(text.getClass(), PACKAGE);
  }

  protected abstract AbstractTextFile newText(final Path path, final Charset charset);
}
