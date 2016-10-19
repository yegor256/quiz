import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class FileImpl implements File {
    private static final int UNICODE_CHAR_LIMIT = 0x80;
    private final java.io.File file;

    public FileImpl(java.io.File file) {
        this.file = file;
    }

    @Override
    public String getContent(boolean ignoreUnicodeCharacters) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = fileInputStream.read()) > 0) {
                if (ignoreUnicodeCharacters && data < UNICODE_CHAR_LIMIT) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }

    @Override
    public void saveContent(String content) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                fileOutputStream.write(content.charAt(i));
            }
        }
    }

}
