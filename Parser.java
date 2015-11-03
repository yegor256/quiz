import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class is thread safe.
 * 1) Format code
 * 2) synchronized keyword is not a good idea. We can use ReentrantReadWriteLock. It will make more performance and scalability.
 * 3) If read files by bytes we must specify charset.
 * 4) If file content is big. We need read file like a stream.
 */
public class Parser {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        try (FileInputStream i = new FileInputStream(file)) {//close resource after usage. Field file is not synchronized access
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = i.read()) > 0) {//maybe > -1 ?
                output.append((char) data);// in this situation charset is default. When append char to String Charset is needed.
            }
            return output.toString();
        }
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        try (FileInputStream i = new FileInputStream(file)) {//Field file is not synchronized access
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }

    public synchronized void saveContent(String content) throws IOException {
        Files.write(file.toPath(), content.getBytes());//need specify charset in method getBytes. Field file is not synchronized access
    }
}
