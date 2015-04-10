package com.isoftstone.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is thread safe.
 */
public class Parser {

    public String getContent(File file) {
        FileInputStream i = null;
        StringBuffer output = new StringBuffer();
        try {
            i = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int data = 0;
            while ((data = i.read(buf)) > 0) {
                output.append(new String(buf, 0, data));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            this.closeQuietly(i);
        }
        return output.toString();
    }

    public String getContentWithoutUnicode(File file) throws IOException {
        FileInputStream i = null;
        StringBuffer output = new StringBuffer();
        try {
            i = new FileInputStream(file);
            int data = 0;
            while ((data = i.read()) > 0) {
                if(data < 0x80) {
                    output.append((char)data);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            this.closeQuietly(i);
        }
        return output.toString();
    }
    
    private void closeQuietly(InputStream is) {
        try {
            is.close(); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    public void saveContent(String content, File file) throws IOException {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file)); 
            out.write(content);
        } finally {
            out.close();
        }
    }
}
