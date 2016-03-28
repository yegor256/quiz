
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
    // 32Kb buffer size
    private static final int BUFFER_SIZE = 32*1024;

    // Final object
    private final File file;

    // init File object on constructor
    public Parser(String path) {
        file = new File(path);
    }

    // specific charset
    public String getContent(String charset) throws IOException {
        // syncronize file object
        synchronized (file) {
            // auto close resources
            try (FileInputStream i = new FileInputStream(file)) {
                // use StringBuilder
                StringBuilder output = new StringBuilder();
                // use buffers
                byte[] data = new byte[BUFFER_SIZE];
                int readed;
                while ((readed = i.read(data)) != -1) {
                    output.append(new String(data, 0, readed, charset));
                }
                return output.toString();
            }
        }
    }

    public String getContentWithoutUnicode() throws IOException {
         // syncronize file object
        synchronized (file) {
            // auto close resources
            try (FileInputStream i = new FileInputStream(file)) {
                // use StringBuilder
                StringBuilder output = new StringBuilder();
                int data;
                while ((data = i.read()) != -1) {
                    if (data < 0x80) {
                        output.append((char) data);
                    }
                }
                return output.toString();
            }
        }
    }

    // specific charset
    public void saveContent(String content, String charset) throws IOException {
        // syncronize file object
        synchronized (file) {
            // auto close resources
            try (FileOutputStream o = new FileOutputStream(file)) {
                // write with specific charset
                o.write(content.getBytes(charset));
                // flush data before close
                o.flush();
            }
        }
    }
}
