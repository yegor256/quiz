import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    public synchronized File getFile() {
        return file;
    }

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized String getContent() throws IOException {
        if (file == null) {
            throw new IllegalStateException("No file provided to the parser");
        }
        FileInputStream i = null;
        String output = "";
        try {
            i = new FileInputStream(file);
            int data;
            while ((data = i.read()) > -1) {
                output += (char) data;
            }
        } finally {
            if (i != null) {
                i.close();
            }
        }
        return output;
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        if (file == null) {
            throw new IllegalStateException("No file provided to the parser");
        }

        FileInputStream i = null;
        String output = "";
        try {
            i = new FileInputStream(file);
            int data;
            while ((data = i.read()) > -1) {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
        } finally {
            if (i != null) {
                i.close();
            }
        }
        return output;
    }

    public synchronized void saveContent(String content) throws IOException {
        if (file == null) {
            throw new IllegalStateException("No file provided to the parser");
        }
        if (content == null) {
            throw new IllegalArgumentException("Null content provided");
        }
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(file);
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } finally {
            if (o != null) {
                o.close();
            }
        }
    }
}
