package io.teamed.quizz;

import lombok.NonNull;
import lombok.Value;

@Value
class PrintableText implements ReadableText {
  @NonNull
  ReadableText delegate;

  @Override
  public String read() {
    return delegate
        .read()
        .replaceAll("[^\\p{Print}]", "");
  }
}
