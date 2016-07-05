package quiz;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

    private File file;

    public synchronized @Nullable File getFile() {
        return file;
    }

    private Parser(File file) {
        this.file = file;
    }

    public static Parser createWithFile(File file) {
        return new Parser(file);
    }

    public String getContent() throws IOException {
        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            output += (char) data;
        }
        return output;
    }

    public String getContentWithoutUnicode() throws IOException {
        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (isByteNotUnicode(data)) {
                output += (char) data;
            }
        }
        return output;
    }

    public void saveContent(String content) throws IOException {
        FileOutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }

    private static boolean isByteNotUnicode(int data) {
        return data < 0x80;
    }
}
