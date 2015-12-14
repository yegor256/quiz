import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

    private File file;

    public Parser(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        try (FileInputStream i = new FileInputStream(file)) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = i.read()) > 0) {
                output.append((char) data);
            }
            return output.toString();
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        try (FileInputStream i = new FileInputStream(file)) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }

    public void saveContent(String content) throws IOException {
        try (FileWriter fw = new FileWriter(file); BufferedWriter buf = new BufferedWriter(fw)) {
            buf.write(content);
        }
    }
}