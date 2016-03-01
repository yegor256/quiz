package io.teamed.quizz;

import static com.google.common.base.Throwables.propagate;
import static java.nio.file.Files.readAllBytes;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

class ReadableTextFile extends AbstractTextFile implements ReadableText {

  protected ReadableTextFile(final Path path, final Charset charset) {
    super(path, charset);
  }

  @Override
  public String read() {
    try {
      return new String(readAllBytes(getPath()), getCharset());
    } catch (final IOException e) {
      throw propagate(e);
    }
  }
}
