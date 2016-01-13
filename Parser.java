import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 * @author Alexey Krylov
 * @since 14.01.2016
 */
public class Parser {
    /**
     * ASCII character set ending.
     */
    private static final int C_ASCII_END = 0x80;
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        try (FileInputStream i = new FileInputStream(file)) {
            StringBuilder sb = new StringBuilder();
            int data;
            while ((data = i.read()) > 0) {
                sb.append((char) data);
            }

            return sb.toString();
        }
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        try (FileInputStream i = new FileInputStream(file)) {
            StringBuilder sb = new StringBuilder();
            int data;
            while ((data = i.read()) > 0) {
                if (data < C_ASCII_END) {
                    sb.append((char) data);
                }
            }

            return sb.toString();
        }
    }

    public synchronized void saveContent(String content) throws IOException {
        try (FileOutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
    }
}