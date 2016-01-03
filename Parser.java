
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is thread safe.
 */
public class Parser {

    private File file;

    private final Object lock = new Object();

    public void setFile(File f) {
        synchronized (lock) {
            file = f;
        }
    }

    public File getFile() {
        synchronized (lock) {
            return file;
        }
    }

    public final String getContent() throws IOException {
        // use StringBuffer, since it is thread-safe
        StringBuffer sb = new StringBuffer();
        // assume that we read from text file
        synchronized (lock) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String output = null;
                while ((output = reader.readLine()) != null) {
                    sb.append(output);
                }
            }
        }

        return sb.toString();
    }

    public String getContentWithoutUnicode() throws IOException {
        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }

    public void saveContent(String content) throws IOException {
        synchronized (lock) {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
                writer.print(content);
            }
        }

    }
}
