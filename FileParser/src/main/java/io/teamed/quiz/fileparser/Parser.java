/*
 * Copyright 2016, James Michael DuPont
 * Derived from unlicensed software https://github.com/teamed/quiz
 * For submission as refactoring example.
 */
package io.teamed.quiz.fileparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * File parser.
 *
 * <p>
 * The class is immutable and thread-safe.
 *
 * @author James Michael DuPont (jamesmikedupont@gmail.com)
 * @version $Id$
 * @since 1.0
 * @see https://github.com/teamed/quiz
 *
 */
public class Parser {

    private final transient File file;
    
    /**
     * size of a block to read or write for efficency
    **/
    
    private final int BLOCKSIZE=4096;
    
    /**
     * cut off chars greater than this
    **/
    private final int UTFCUT= 0x80;
    
    /**
     * Ctor.
     *
     * @param file Text file to parse
     * @throws java.io.IOException
     */
    public Parser(final File file) throws IOException {
        this.file = file;
    }
    
  /**
     * Get content of file.
     * reads in blocks    
     * @return String of contentes
     * @throws java.io.IOException
     */
    public String getContent() throws IOException {
        final FileInputStream input = new FileInputStream(file);        
        String output;
        output = "";
        final byte[] buf = new byte[BLOCKSIZE];
        while (true) {
            final int chars = input.read(buf);
            int obytes = 0;
            if (chars < 0) {
                break;
            }            
            final String tempstr  = new String(buf,StandardCharsets.UTF_8);
            output += tempstr;
        }
        return output;         
    }

    /**
     * Get content of file stripping off chars above the UTFCUT
     * reads in blocks, copies to new block
     * @return String of contentes
     * @throws java.io.IOException
     */
    public String getContentWithoutUnicode() throws IOException {
        final FileInputStream input = new FileInputStream(file);        
        String output;
        output = "";
        final byte[] buf = new byte[BLOCKSIZE];
        final byte[] obuf = new byte[BLOCKSIZE];
        
        while (true) {
            final int chars = input.read(buf);
            int obytes = 0;
            if (chars < 0) {
                break;
            }           
            for (int pos = 0; pos < chars; pos++) {
                final int data;
                data = buf[pos];
                if ((data < UTFCUT) && (data > 0)) {
                    obuf[obytes++]=(byte)data;
                } 
                
            }
            final String tempstr  = new String(obuf,StandardCharsets.UTF_8);
            output += tempstr;
        }
        return output;       
    }

    public void saveContent(String content) throws IOException {
        try (FileOutputStream o = new FileOutputStream(file)) {
            try (Writer out = new OutputStreamWriter(o, StandardCharsets.UTF_8)) {
                out.write(content);
            }    
        }
    }
}
