package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {

private static Parser instance;

private File file;

private FileInputStream fileInputStream;
private FileOutputStream fileOutPutStream;
private String output = "";
private int data;

    private Parser() {}

    public static synchronized Parser getInstance(File f) {
        if(instance == null) {
            instance = new Parser();
            instance.setFile(f);
        }
        return instance;
    }

  private void setFile(File f) {
    if(this.file == null){
      this.file = f;
    }
    
  }
  public File getFile() {
    return this.file;
  }
  public String getContent() throws IOException {
    this.fileInputStream = new FileInputStream(this.file);
    while ((this.data = this.fileInputStream.read()) > 0) {
      this.output += (char) this.data;
    }
    this.fileInputStream.close();
    return this.output;
  }
  public String getContentWithoutUnicode() throws IOException {
    this.fileInputStream = new FileInputStream(this.file);
    while ((this.data = this.fileInputStream.read()) > 0) {
      if (this.data < 0x80) {
        this.output += (char) this.data;
      }
    }
    this.fileInputStream.close();
    return this.output;
  }
  public void saveContent(String content) throws IOException {
    this.fileOutPutStream = new FileOutputStream(this.file);
    for (int i = 0; i < content.length(); i += 1) {
      this.fileOutPutStream.write(content.charAt(i));
    }
    this.fileOutPutStream.close();
  }
}

