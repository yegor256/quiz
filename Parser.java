import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is immutable.
 */
public class Parser {

    private final File file;
    private String content;
    private String contentWithoutUnicode;

    public Parser(File file) {
        this.file = file;
    }

    public String getContent() {
        if (content != null) return content;
        content = getContentInternal(true);
        return content;
    }

    public String getContentWithoutUnicode() {
        if (contentWithoutUnicode != null) return contentWithoutUnicode;
        contentWithoutUnicode = getContentInternal(false);
        return contentWithoutUnicode;
    }

    private String getContentInternal(boolean unicode) {
        FileInputStream fis = loadStream();
        StringBuilder sb = new StringBuilder();
        int data;
        try {
            while ((data = fis.read()) > 0) {
                if (unicode) sb.append(data);
                else if (data < 0x80) {
                    sb.append(data);
                }
            }
        } catch (IOException e) {
            closeStream(fis);
            throw new RuntimeException(/* appropriate message */);
        }
        closeStream(fis);
        return sb.toString();
    }

    private FileInputStream loadStream() {
        FileInputStream fis;        
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(/* appropriate message */);
        }
        return fis;
    }

    private void closeStream(FileInputStream fis) {
        try {
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(/* appropriate message */);
        }
    }
    public void saveContent(String content) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(/* appropriate message */);
        }

        try {
            bos.write(content.getBytes());
            bos.close();
        } catch (IOException e) {
            throw new RuntimeException(/* appropriate message */);
        }
    }
}
