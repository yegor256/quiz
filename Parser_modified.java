package testapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */

public class Parser {
    
   //class level variables have underscore _
  private File _file;
  public synchronized void setFile(File file) {
    _file = file;
  }
  public synchronized File getFile() {
    return _file;
  }
  
  public String getContent() throws IOException {
    FileInputStream fileInputStream = null;
    String output = "";    
    try
    {
        fileInputStream = new FileInputStream(_file);
        int data;
        while ((data = fileInputStream.read()) > 0) {
          output += (char) data;
        }
    }
    finally{
        if(fileInputStream != null)
            fileInputStream.close();
    }
    
    return output;
  }
  
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream fileInputStream = null;
    String output = "";
    try
    {
        fileInputStream = new FileInputStream(_file);
        int data;
        while ((data = fileInputStream.read()) > 0) {
          if (data < 0x80) {
            output += (char) data;
          }
        }
     }
    finally{
        if(fileInputStream != null)
            fileInputStream.close();
    }
    return output;
  }
  
  public void saveContent(String content) throws IOException {
    FileOutputStream fileOutputStream = null;
    try
    {
        fileOutputStream = new FileOutputStream(_file);
        for (int i = 0; i < content.length(); i += 1) {
          fileOutputStream.write(content.charAt(i));
        }
    }
    finally{    
        if(fileOutputStream != null)
            fileOutputStream.close();
    }
  }
  
}
