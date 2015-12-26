import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class is thread safe.
 */
public class Parser {

    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        validate();

        Scanner s = null;
        try {
            s = new Scanner(file);
            return s.useDelimiter("\\Z").next(); // DRY
        }
        finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        validate();

        return getContent().replaceAll("[^\\x00-\\x7F]", ""); // reuse get content code
    }

    public synchronized void saveContent(String content) throws IOException {
        validate();

        if (content == null) {
            throw new NullPointerException("new file content is null");
            //todo: thrown exception or just delete the file?
        }

        PrintWriter pw = null;

        try {
            pw = new PrintWriter(file);
            pw.write(content);

        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private void validate() {
        if (file == null) {
            throw new NullPointerException("file is null");
        }
    }
}
