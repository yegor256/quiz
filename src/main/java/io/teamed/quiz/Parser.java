package io.teamed.quiz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    public synchronized void setFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        return new String(Files.readAllBytes(file.toPath()), "UTF-8");
    }

    public String getContentWithoutUnicode() throws IOException {
        String content = getContent();
        StringBuilder b = new StringBuilder(content.length());
        for (int index = 0; index < content.length(); index++) {
            char c = content.charAt(index);
            if (c < 0x80) {
                b.append(c);
            }
        }
        return b.toString();
    }

    public void saveContent(String content) throws IOException {
        Files.write(file.toPath(), content.getBytes());
    }
}