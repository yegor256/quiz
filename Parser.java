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
    private FileInputStream fileInputStream;

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
        loadFile();
        StringBuilder sb = new StringBuilder();
        int data;
        try {
            while ((data = fileInputStream.read()) > 0) {
                if (unicode) sb.append(data);
                else if (data < 0x80) {
                    sb.append(data);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(/* appropriate message */);
        }
        return sb.toString();
    }

    private void loadFile() {
        if (fileInputStream != null) return;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
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
