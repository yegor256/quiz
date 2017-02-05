import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
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
        try (FileInputStream i = new FileInputStream(file); BufferedInputStream bi = new BufferedInputStream(i)) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = bi.read()) > 0) {
                output.append((char) data);
            }
            return output.toString();
        }
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        try (FileInputStream i = new FileInputStream(file); BufferedInputStream bi = new BufferedInputStream(i)) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = bi.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }

    public synchronized void saveContent(String content) throws IOException {
        try (FileOutputStream o = new FileOutputStream(file);BufferedOutputStream bo = new BufferedOutputStream(o)) {
            for (int i = 0; i < content.length(); i += 1) {
                bo.write(content.charAt(i));
            }
        }
    }
}
