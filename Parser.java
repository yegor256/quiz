package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  public synchronized void setFile(File f) {
    file = f;
  }

  public synchronized File getFile() {
    return file;
  }

  public String getContent() {
    String output = "";
    try (FileInputStream i = new FileInputStream(file)) {
      int data;
      while ((data = i.read()) > 0) {
        output += (char) data;
      }
    } catch (IOException ignored) {
    }
    return output;
  }

  public String getContentWithoutUnicode() {
    String output = "";
    try (FileInputStream i = new FileInputStream(file)) {
      int data;
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output += (char) data;
        }
      }
    } catch (IOException ignored) {
    }
    return output;
  }

  public void saveContent(String content) {
    try (FileOutputStream o = new FileOutputStream(file)) {
      for (int i = 0; i < content.length(); i += 1) {
        o.write(content.charAt(i));
      }
    } catch (IOException ignored) {
    }
  }
}
