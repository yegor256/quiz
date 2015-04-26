package io.team.quiz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

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

    public String getContent() throws IOException {
        final StringBuilder sb = new StringBuilder();
        InputStream is = new FileInputStream(file);
        final InputStreamReader isr = new InputStreamReader(is);
        final BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public String getContentWithoutUnicode() throws IOException {
        final StringBuilder sb = new StringBuilder();
        InputStream is = new FileInputStream(file);
        
        // adding a decoder to igonore unmapped chars (not unicode UTF-8)
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
        decoder.onMalformedInput(CodingErrorAction.IGNORE);

        final InputStreamReader isr = new InputStreamReader(is, decoder);
        final BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            //line.replace();
            sb.append(line);
        }
        return sb.toString();
    }

    public void saveContent(String content) throws IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            output.write(content);
        }
    }
}
