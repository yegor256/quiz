package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * This class is thread safe.
 */
public class Parser {

  private File file;

  private FileInputStream fileInputStream;
  private BufferedInputStream bis;
  private String output;
  private BufferedReader br;
  private StringBuilder strBuilder;
  private BufferedOutputStream writer;
  private String readLine;
  private static final int BUFFER_SIZE = 1024;

  public Parser() {
  }

  public synchronized void setFile(File f) {
    if (this.file == null) {
      this.file = f;
    }

  }

  public synchronized File getFile() {
    return this.file;
  }

  public synchronized String getFileContent() throws IOException {
    try {
      this.fileInputStream = new FileInputStream(this.file);
      this.bis = new BufferedInputStream(fileInputStream);

      this.strBuilder = new StringBuilder();
      InputStreamReader isr = new InputStreamReader(this.bis);

      this.br = new BufferedReader(isr, Parser.BUFFER_SIZE);
      if (br.ready()) {
        while ((readLine = br.readLine()) != null) {
          this.strBuilder.append(readLine + "\n");
        }
      }

    } catch (IOException ex) {
      throw ex;
    } finally {
      if (this.bis != null) {
        this.bis.close();
      }
      if (this.fileInputStream != null) {
        this.fileInputStream.close();
      }
      if (this.br != null) {
        this.br.close();
      }

    }
    this.output = this.strBuilder.toString();

    return this.output;
  }

  public synchronized String getContent() throws IOException {
    try {

      this.output = new String(getFileContent().getBytes(), "UTF8");

    } catch (IOException ex) {
      throw ex;
    }
    return this.output;
  }

  public synchronized String getContentWithoutUnicode() throws IOException {
    try {
      this.output = getFileContent();
    } catch (IOException ex) {
      throw ex;
    }
    return this.output;
  }

  public synchronized void saveContent(String content) throws IOException {
    try {
      this.writer = new BufferedOutputStream(new FileOutputStream(
          this.file));
      this.writer.write(content.getBytes(Charset.forName("UTF-8")));
    } catch (Exception e) {
    } finally {
      if (this.writer != null) {
        this.writer.close();
      }
    }
  }

}