package quiz;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

    private @NotNull File file;

    public synchronized @NotNull File getFile() {
        return file;
    }

    private Parser(File file) {
        this.file = file;
    }

    public static Parser createWithFile(@NotNull File file) throws NullPointerException {
        if (file == null) {
            throw new NullPointerException("File is null");
        }

        return new Parser(file);
    }

    public String getContent() throws IOException {
        FileInputStream i = new FileInputStream(file);
        StringBuilder output = new StringBuilder("");
        int data;
        while ((data = i.read()) > 0) {
            output.append((char) data);
        }
        return output.toString();
    }

    public String getContentWithoutUnicode() throws IOException {
        FileInputStream i = new FileInputStream(file);
        StringBuilder output = new StringBuilder("");
        int data;
        while ((data = i.read()) > 0) {
            if (isByteNotUnicode(data)) {
                output.append((char) data);
            }
        }
        return output.toString();
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
