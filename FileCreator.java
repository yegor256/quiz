import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Author: Fyodor Kemenov
 * Created: 22.04.2015
 * <p>
 * <p>
 * According to the single responsibility principle we should move this logic in separated class.
 * <p>
 */
public class FileCreator {

    private final Charset charset;

    public FileCreator(Charset charset) {
        this.charset = charset;
    }

    public void saveContent(String content, File file) throws IOException {
        Files.write(file.toPath(), content.getBytes(charset));
    }

    public Charset getCharset() {
        return charset;
    }


}
