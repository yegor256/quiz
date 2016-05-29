package com.bibro.parser;

import java.io.IOException;

public class NoUnicodeTextFile implements TextFile {

    private final TextFile textFile;

    public NoUnicodeTextFile(TextFile textFile) {
        this.textFile = textFile;
    }

    @Override
    public String read() throws IOException {
        String content = textFile.read();
        return content.replaceAll("[^\\p{ASCII}]", "");
    }

    @Override
    public void write(String content) throws IOException {
        textFile.write(content);
    }
}
