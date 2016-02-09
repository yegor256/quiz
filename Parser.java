import java.io.*;
import java.nio.charset.Charset;

/**
 * This class is thread safe.
 * This class make more safely and fixed errors.
 */
public class Parser {

    private volatile File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException, OutOfMemoryError {
        Reader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), Charset.defaultCharset()));
        StringBuilder output = new StringBuilder();

        try {
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                output.append(buffer, 0, read);
            }
        } finally {
            reader.close();
        }

        return output.toString();
    }

    public String getContentWithoutUnicode() throws IOException, OutOfMemoryError {
        Reader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), Charset.defaultCharset()));
        StringBuilder output = new StringBuilder();

        try {

            int read;
            while ((read = reader.read()) > 0) {
                if (read < 0x80) {
                    output.append(read);
                }
            }
        } finally {
            reader.close();
        }

        return output.toString();
    }

    public void saveContent(String content) throws IOException {
        FileOutputStream o = new FileOutputStream(file);
        try {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        } finally {
            o.flush();
            o.close();
        }
    }
}
