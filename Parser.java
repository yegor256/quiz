import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class is thread safe.
 */
public class Parser {
    private volatile File file;

    public synchronized void setFile(final File f) {
        file = f;
    }

    public File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        StringBuilder output = new StringBuilder();

        try (InputStream i = new BufferedInputStream(new FileInputStream(file));){
            int data;
            while ((data = i.read()) > -1) {
                output.append((char) data);
            }
        }

        return output.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new BufferedInputStream(new FileInputStream(file));) {
            int data;
            while ((data = i.read()) > -1) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }

    public synchronized void saveContent(final String content) throws IOException {
        try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file));) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
            o.flush();
        }
    }
}
