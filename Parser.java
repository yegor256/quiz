package com.company;

import java.io.*;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Scanner;

/**
 * This class is thread safe.
 */
public class Parser {
  private File file;

  public Parser(File file){
    if(file == null || !file.exists()){
        throw new IllegalArgumentException();
    }
      this.file = file;
  }

  private synchronized String read(boolean withUnicode) throws java.io.IOException{
    StringBuilder sb = new StringBuilder();
    FileInputStream inputStream = null;
    Scanner sc = null;
    try {
      inputStream = new FileInputStream(this.file);
      sc = new Scanner(inputStream, "UTF-8");
      while (sc.hasNextLine()) {
        String line;
        if(!withUnicode){
          line = sc.nextLine().replaceAll("\\p{C}", "");
        }else {
          line = sc.nextLine();
        }
        sb.append(line);
      }
      if (sc.ioException() != null) {
        throw sc.ioException();
      }
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
      if (sc != null) {
        sc.close();
      }
    }
    return sb.toString();
  }

  public synchronized String getContent() throws IOException {
    return read(true);
  }
  public synchronized String getContentWithoutUnicode() throws IOException {
    return read(false);
  }
  public void saveContent(String content) throws IOException {
      FileOutputStream fileOutputStream = new FileOutputStream(this.file);
      boolean written = false;
      do {
          try{
              FileLock lock = fileOutputStream.getChannel().lock();
              try{
                  fileOutputStream.write(content.getBytes());
                  written = true;
              }finally {
                  lock.release();
              }
          }catch (OverlappingFileLockException e){
              try{
                  Thread.sleep(100);
              }catch (InterruptedException ex){
                  throw new InterruptedIOException("waiting for lock");
              }
          }
      }while(!written);
  }
}
