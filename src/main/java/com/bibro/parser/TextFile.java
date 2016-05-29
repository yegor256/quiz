package com.bibro.parser;

import java.io.IOException;

public interface TextFile {
    String read() throws IOException;
    void write(String content) throws IOException;
}
