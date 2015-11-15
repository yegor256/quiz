package com.cisatchmo.test.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    private Parser() {
    }

    public static Parser createParser() {
        return new Parser();
    }

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent(boolean withUnicode) throws IOException {
        FileInputStream i = new FileInputStream(getFile());
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = i.read()) > 0) {
            if (!withUnicode) {
                if (data < 0x80)
                    output.append((char) data);
            } else {
                output.append((char) data);
            }
        }
        return output.toString();
    }

    public synchronized void saveContent(String content) throws IOException {
        BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(getFile()));
        try {
            o.write(content.getBytes());
        } finally {
            o.close();
        }
    }


}
