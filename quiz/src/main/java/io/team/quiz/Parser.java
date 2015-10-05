package io.team.quiz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

/**
 * This class is thread safe.
 */
public class Parser {

    private File file;
    private final CharsetDecoder decoder;

    public Parser() {
        decoder = StandardCharsets.UTF_8.newDecoder();
        decoder.onMalformedInput(CodingErrorAction.IGNORE);
    }

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public Parser(File file, CharsetDecoder decoder) {
        this.file = file;
        this.decoder = decoder;
    }

    public Parser(CharsetDecoder decoder) {
        this.decoder = decoder;
    }
    
    public String getContent() throws IOException {
        return read(new InputStreamReader(new FileInputStream(file)));
    }

    public String getContentWithoutUnicode() throws IOException {
        return read(new InputStreamReader(new FileInputStream(file), decoder));
    }

    public void saveContent(String content) throws IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            output.write(content);
        }
    }

    private String read(InputStreamReader isr) throws IOException {
        final BufferedReader br = new BufferedReader(isr);
        final StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
