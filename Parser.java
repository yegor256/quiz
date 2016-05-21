import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Parser {

    private final File file;

    public Parser(String filePath) {
        this.file = new File(filePath);
    }

    public synchronized String getContent(boolean withUnicode) throws IOException {
        FileInputStream i = new FileInputStream(file);
        StringBuilder output = new StringBuilder();
        try {
            int data;
            while ((data = i.read()) != -1) {
                if (withUnicode) {
                    output.append((char) data);
                } else {
                    if (data < 0x80) {
                        output.append((char) data);
                    }
                }
            }
            return output.toString();
        } finally {
            i.close();
        }
    }

    public synchronized void saveContent(String content) throws IOException {
        if (content == null || content.length() == 0) {
            throw new RuntimeException("Invalid content");
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(content.getBytes());
            fos.flush();
        } finally {
            fos.close();
        }
    }
}
