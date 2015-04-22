import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;


/**
 * This class is thread safe in a matter that data stored in it is safe,
 * but it's not safe to read/write data from a single file simultaneously.
 *
 * Next step: utilize NIO API for file reading/writing
 */
public class Parser {
    private static final CharsetEncoder asciiEncoder =
    Charset.forName("US-ASCII").newEncoder();
    
    private File file;
    
    public synchronized void setFile(File f) {
        file = f;
    }
    
    public synchronized File getFile() {
        return file;
    }
    
    public String getContent() throws IOException {
        File local;
        synchronized (this) {
            local = this.file;
        }
        
        try (FileInputStream fis = new FileInputStream(local);
             StringWriter output = new StringWriter(fis.available())) {
            int data;
            while ((data = fis.read()) > 0) {
                output.write(data);
            }
            return output.toString();
        }
    }
    
    public String getContentWithoutUnicode() throws IOException {
        File local;
        synchronized (this) {
            local = this.file;
        }
        
        try (FileInputStream fis = new FileInputStream(local);
             StringWriter output = new StringWriter(fis.available())) {
            int data;
            while ((data = fis.read()) > 0) {
                if (asciiEncoder.canEncode((char)data)) {
                    output.write(data);
                }
            }
            return output.toString();
        }
    }
    
    public void saveContent(String content) throws IOException {
        File local;
        synchronized (this) {
            local = this.file;
        }
        
        try (FileWriter writer = new FileWriter(local);
             BufferedWriter output = new BufferedWriter(writer)) {
            output.write(content);
        }
    }
}