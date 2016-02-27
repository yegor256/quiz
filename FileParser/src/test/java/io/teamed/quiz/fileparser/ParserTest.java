/*
 * Copyright 2016, James Michael DuPont
 * All rights reserved
 * For submission as refactoring example.
 * the ascii file is from http://www.ietf.org/rfc/rfc4251.txt
 * the utf8 example is from https://wikisource.org/wiki/Drejtshkrimi_Prishtinë_1957
 */
package io.teamed.quiz.fileparser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class ParserTest {

    final String ASCII_FILE = "src/test/resources/rfc4251.txt";
    final String UTF_FILE = "src/test/resources/Drejtshkrimi Prishtinë 1957 - Wikisource.html";
    final File ascii_file = new File(ASCII_FILE);
    final File utf8_file = new File(UTF_FILE);

    @Test
    public void testParser() {
        try {
            Parser parser = new Parser(ascii_file);             
        } catch (IOException ex) {
            Logger.getLogger(ParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetContent() {
        try {
            Parser parser = new Parser(ascii_file);
            String content = parser.getContent();
            assertTrue(content.contains("Valimotie 17"));
        } catch (IOException ex) {
            Logger.getLogger(ParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetContentUnicode() {
        try {
            Parser parser = new Parser(utf8_file);
            String content = parser.getContent();
            assertTrue(content.contains("ë"));
            
            //Logger.getLogger(ParserTest.class.getName()).log(Level.INFO, content);
        } catch (IOException ex) {
            Logger.getLogger(ParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetContentWithoutUnicode() {
        try {
            Parser parser = new Parser(utf8_file);
            String content = parser.getContentWithoutUnicode();
            assertTrue(!content.contains("ë"));
            //Logger.getLogger(ParserTest.class.getName()).log(Level.INFO, content);
        } catch (IOException ex) {
            Logger.getLogger(ParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testSaveContentAscii() {
        File ofile = new File("/tmp/test2-stripped.txt");
    try {
            Parser parser = new Parser(ascii_file);
            Parser oparser = new Parser(ofile);
            String content = parser.getContentWithoutUnicode();
            oparser.saveContent(content);
        } catch (IOException ex) {
            Logger.getLogger(ParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testSaveContent4() {
        File ofile = new File("/tmp/utf-stripped.html");
        try {
            Parser parser = new Parser(utf8_file);
            Parser oparser = new Parser(ofile);
            String content = parser.getContentWithoutUnicode();
            oparser.saveContent(content);
        } catch (IOException ex) {
            Logger.getLogger(ParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testSaveContent2() {
        File ofile = new File("/tmp/utf-full.html");
        try {
            Parser parser = new Parser(utf8_file);
            Parser oparser = new Parser(ofile);
            String content = parser.getContent();
            oparser.saveContent(content);
        } catch (IOException ex) {
            Logger.getLogger(ParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
