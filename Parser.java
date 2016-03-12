package com.amit.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  public Parser(File file){
	  if(file==null){
		  throw new IllegalArgumentException("file can not be null");
	  }
	  this.file = file;
  }
  public synchronized void setFile(File f) {
    this.file = f;
  }
  public File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    StringBuilder output = new StringBuilder();
    BufferedReader br = new BufferedReader(new FileReader(this.file));
    String line=null;
    while ((line = br.readLine()) != null) {
       output.append(line);
    }
    br.close();
    return output.toString();
  }
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    StringBuilder output = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
    	  output.append((char) data);
      }
    }
    i.close();
    return output.toString();
  }
  public void saveContent(String content) throws IOException {
    
    if (!this.file.exists()) {
		this.file.createNewFile();
	}
	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(content);
	bw.close();
  }
}