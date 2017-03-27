package parser;

import java.io.*;

public final class File {

    private final java.io.File file;

    public File(final String path) {
        file = new java.io.File(path);
    }

    public final synchronized String read() throws IOException {
        final StringBuilder content = new StringBuilder();

        try (FileReader reader = new FileReader(file)) {
            for (int data = reader.read(); data > 0; data = reader.read()) {
                content.append((char) data);
            }
        }

        return content.toString();
    }

    public final synchronized void write(final String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content.toCharArray());
        }
    }

}
