package net.ligazakon.billing.routing.service.parser;

import scala.Char;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by shopinp on 04.02.16.
 */
public class Parser {
    private File file;
    private String filePath;
    private String content;
    private String charset = "utf-8";

    private static Parser parser = null;

    private Parser(){}

    private Parser(String parsingFilePath)
    {
        this();
        this.filePath = parsingFilePath;
        file = new File(parsingFilePath);
    }

    public static synchronized Parser getInstance(String parsingFilePath)
    {
        if(null == parser)
        {
            parser = new Parser(parsingFilePath);
        }
        return parser;
    }

    public String getContent() throws IOException {
        synchronized(file.getCanonicalPath().intern()){
            if(null == content)
            {
                content = readContent();
            }
            return content;
        }
    }
    private String readContent() throws IOException {
        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }
    public String getContentWithoutUnicode() throws IOException {
        synchronized (file.getCanonicalPath().intern()){
            FileInputStream i = new FileInputStream(file);
            String output = "";
            int data;
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
            return output;
        }
    }
    public void saveContent(String newContent) throws IOException {
        synchronized (file.getCanonicalPath().intern()){
            FileOutputStream o = new FileOutputStream(file);
            for (int i = 0; i < newContent.length(); i += 1) {
                o.write(newContent.charAt(i));
            }
            content = readContent();
        }
    }
}
