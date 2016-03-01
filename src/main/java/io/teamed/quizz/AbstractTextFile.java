package io.teamed.quizz;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.nio.file.Files.isRegularFile;
import static lombok.AccessLevel.PRIVATE;

import java.nio.charset.Charset;
import java.nio.file.Path;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level=PRIVATE, makeFinal=true)
abstract class AbstractTextFile {

  Path path;
  Charset charset;

  protected AbstractTextFile(final Path path, final Charset charset) {
    checkArgument(isRegularFile(path));
    this.path = checkNotNull(path);
    this.charset = checkNotNull(charset);
  }

  protected final Charset getCharset() {
    return charset;
  }

  protected final Path getPath() {
    return path;
  }
}
