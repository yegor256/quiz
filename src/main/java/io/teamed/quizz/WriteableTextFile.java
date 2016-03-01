package io.teamed.quizz;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.propagate;
import static java.nio.file.Files.write;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

final class WriteableTextFile extends AbstractTextFile implements WriteableText {

  private final StandardOpenOption[] options;

  protected WriteableTextFile(
      final Path path, 
      final Charset charset,
      final StandardOpenOption... options) {
    super(path, charset);
    this.options = checkNotNull(options);
  }

  @Override
  public void save(final String content) {
    final byte[] bytes = content.getBytes(getCharset());
    final Path path = getPath();
    try {
      write(path, bytes, options);
    } catch(final IOException e) {
      throw propagate(e);
    }
  }

}
