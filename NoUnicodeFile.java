package parser;

import java.io.IOException;

public final class NoUnicodeFile {

    private final File file;

    public NoUnicodeFile(final String path) {
        file = new File(path);
    }

    public final String read() throws IOException {
        return toNoUnicodeContent(file.read());
    }

    public final void write(final String content) throws IOException {
        file.write(toNoUnicodeContent(content));
    }

    private String toNoUnicodeContent(final String content) {
        final StringBuilder noUnicodeContent = new StringBuilder();

        for (char data : content.toCharArray()) {
            if (data < 0x80) {
                noUnicodeContent.append(data);
            }
        }

        return noUnicodeContent.toString();
    }

}
