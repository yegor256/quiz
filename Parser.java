import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class is thread safe.
 */
public class Parser {
    private static final String ENCODING = "UTF-8";
    // Use an atomic reference to get rid of the overhead of a lock on the current instance
    private final AtomicReference<File> ref = new AtomicReference<>();
    public void setFile(File f) {
        ref.set(f);
    }
    public File getFile() {
        return ref.get();
    }
    public String getContent() throws IOException {
        // Store the result in a StringBuilder instead of using String concatenation.
        StringBuilder output = new StringBuilder();
        // Use a buffer to speed up the file reading
        byte[] buffer = new byte[1024];
        int length;
        File file = getFile();
        // Use a try-resource block to close all the Closable resources
        try (FileInputStream i = new FileInputStream(file)) {
            while ((length = i.read(buffer)) != -1) {
                // Specify the target encoding explicitly to prevent unexpected behavior
                output.append(new String(buffer, 0, length, ENCODING));
            }
        }
        return output.toString();
    }
    public String getContentWithoutUnicode() throws IOException {
        // Store the result in a StringBuilder instead of using String concatenation.
        StringBuilder output = new StringBuilder();
        int data;
        File file = getFile();
        // Use a try-resource block to close all the Closable resources
        try (FileInputStream i = new FileInputStream(file)) {
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }
    public void saveContent(String content) throws IOException {
        File file = getFile();
        try (FileOutputStream o = new FileOutputStream(file)) {
            // Encode directly the content no need to bother with a for loop
            o.write(content.getBytes(ENCODING));
        }
    }
}