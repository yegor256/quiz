import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Parser {
    private File file;

    public synchronized File getFile() {
        return file;
    }

    public static synchronized void setFile(File f) {
        file = f;
    }

    public String getContent() throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    public String getContentWithoutUnicode() throws IOException {
        return new String(Files.readAllBytes(file.toPath()), Charset.forName("UTF-8"));
    }

    public void saveContent(String content) throws IOException {
        FileOutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}
