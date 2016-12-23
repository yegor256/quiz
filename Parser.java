import com.sun.org.apache.regexp.internal.recompile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * This class is thread safe.
 */
public class Parser {
    private Path filePath;

    public synchronized void setFile(File file) {
        filePath = file.toPath();
    }

    public synchronized File getFile() {
        return filePath.toFile();
    }

    public synchronized Path getPath() {
        return this.filePath;
    }

    public synchronized void setPath(Path path) {
        this.filePath = path;
    }


    public Parser(Path path) {
        this.filePath = path;
    }

    /**
     * @return
     * @throws IOException
     * @throws java.lang.NullPointerException if file = null
     * @throws java.io.FileNotFoundException  if file not exists
     */
    public synchronized String getContent() throws IOException {
        Charset charset = Charset.defaultCharset();
        Scanner scanner = new Scanner(filePath, charset.name());
        scanner.next();
        StringBuilder destinationStringBuilder = new StringBuilder();
        String read = null;
        while ((read = scanner.next()) != null) {
            destinationStringBuilder.append(read);
        }
        return destinationStringBuilder.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(filePath, Charset.defaultCharset());
        StringBuilder destinationStringBuilder = new StringBuilder();
        int data;
        while ((data = bufferedReader.read()) > 0 && (data < 0x80)) {
            destinationStringBuilder.append((char) data);
        }
        return destinationStringBuilder.toString();
    }

    public synchronized void saveContent(String content) throws IOException {
        BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath, Charset.defaultCharset());
        bufferedWriter.write(content);
        bufferedWriter.flush();
    }
}
