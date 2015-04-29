package com.example;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {
  @Nullable
  private volatile File file;

  public void setFile(@Nullable File f) {
    file = f;
  }

  @Nullable
  public File getFile() {
    return file;
  }

  @NotNull
  public String getContent() throws IOException {
    File file = this.file;
    if (file == null) throw new IOException("File is null");
    InputStream i = new FileInputStream(file);
    try {
      StringBuilder output = new StringBuilder();
      int data;
      while ((data = i.read()) > 0) {
        output.append((char) data);
      }
      return output.toString();
    }
    finally {
      i.close();
    }
  }

  @NotNull
  public String getContentWithoutUnicode() throws IOException {
    File file = this.file;
    if (file == null) throw new IOException("File is null");
    InputStream i = new FileInputStream(file);
    try {
      StringBuilder output = new StringBuilder();
      int data;
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output.append((char) data);
        }
      }
      return output.toString();
    }
    finally {
      i.close();
    }
  }

  public void saveContent(@NotNull String content) throws IOException {
    File file = this.file;
    if (file == null) throw new IOException("File is null");
    OutputStream o = new FileOutputStream(file);
    try {
      for (int i = 0; i < content.length(); i += 1) {
        o.write(content.charAt(i));
      }
    }
    finally {
      o.close();
    }
  }
}
