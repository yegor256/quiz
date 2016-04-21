import java.io.*;

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

    public String getContent() throws IOException {
        try (FileInputStream i = new FileInputStream(file)) {
            String output = "";
            int data;
            while ((data = i.read()) > 0) {
                output += (char) data;
            }
            return output;
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent().replaceAll("[^\\x00-\\x7F]", "");
    }

    public void saveContent(String content) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(content);
        }
    }
}
