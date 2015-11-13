import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
    private volatile File file;
    
    public synchronized void setFile(File file) {
        this.file = file;
    }
    
    public File getFile() {
        return file;
    }
    
    public String getContent() throws IOException {
        FileInputStream inputStream = null;
        StringBuilder outputBuilder = null;
        try {
            inputStream = new FileInputStream(file);
            outputBuilder = new StringBuilder();
            int data;
            while ((data = inputStream.read()) > 0) {
                outputBuilder.append((char) data);
            }
        } finally {
            inputStream.close();
        }
        return outputBuilder.toString();
    }
    
    public String getContentWithoutUnicode() throws IOException {
        FileInputStream inputStream = null;
        StringBuilder outputBuilder = null;
        try {
            inputStream = new FileInputStream(file);
            outputBuilder = new StringBuilder();
            int data;
            while ((data = inputStream.read()) > 0) {
                if (data < 0x80) {
                    outputBuilder.append((char) data);
                }
            }
        } finally {
            inputStream.close();
        }
        
        return outputBuilder.toString();
    }
    
    public void saveContent(String content) throws IOException {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            for (int i = 0; i < content.length(); ++i) {
                outputStream.write(content.charAt(i));
            }
        } finally {
            outputStream.close();
        }
        
    }
}