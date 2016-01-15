import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Represents thread-safe file parser.
 *
 * @author Alexey Krylov
 * @since 14.01.2016
 */
public class Parser {
    protected File file;

    public File getFile() {
        return file;
    }

    public Parser(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("Specified file is null");
        }

        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException(String.format("File [%s] exists but is a directory", file));
            }
            if (!file.canRead()) {
                throw new IOException(String.format("File [%s] is unreadable", file));
            }
        } else {
            throw new FileNotFoundException(String.format("File [%s] does not exists", file));
        }
        this.file = file;
    }

    public String parse() throws IOException {
        try (FileInputStream i = new FileInputStream(file)) {
            StringBuilder sb = new StringBuilder();
            int data;
            while ((data = i.read()) > 0) {
                sb.append((char) data);
            }

            return sb.toString();
        }
    }
}