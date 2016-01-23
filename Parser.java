import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;
    public void setFile(File f) {
        file = f;
    }
    public synchronized String getContent() throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }
    public String getContentWithoutUnicode() throws IOException {
        String content = getContent();
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < content.length(); i++) {
            char character = content.charAt(i);
            if (character < 0x80) {
                output.append(character);
            }
        }
        return output.toString();
    }
    public synchronized void saveContent(String content) throws IOException {
        try (FileOutputStream output = new FileOutputStream(file)) {
            output.write(content.getBytes());
            output.close();
        }
    }
}
