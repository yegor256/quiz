package com.test;

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

    public String getContent() throws Exception {
        if (file == null){
            throw  new Exception("file object is null");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();
        try {
            int data;
            while ((data = fileInputStream.read()) > 0) {
                sb.append((char) data);
            }
        }finally {
            fileInputStream.close();
        }
        return sb.toString();
    }

    public String getContentWithoutUnicode() throws Exception {
        if (file == null){
            throw  new Exception("file object is null");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();
        try {
            int data;
            while ((data = fileInputStream.read()) > 0) {
                if (data < 0x80) {
                    sb.append((char) data);
                }
            }
        } finally {
            fileInputStream.close();
        }
        return sb.toString();
    }

    public void saveContent(String content) throws Exception {
        if (file == null){
            throw  new Exception("file object is null");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            for (int i = 0; i < content.length(); i += 1) {
                fileOutputStream.write(content.charAt(i));
            }
        }finally {
            fileOutputStream.close();
        }
    }
}
