package ch.fware.teamed.quiz;

import java.io.IOException;

public interface Parser {

    String getContent() throws IOException;

    String getContentWithoutUnicode() throws IOException;

    void saveContent(String content) throws IOException;
}