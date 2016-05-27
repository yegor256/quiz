import java.io.*;

/**
 * This class is thread safe, i.e. only one thread can read or write from/to wrapped file.
 */
//TODO add java concurrency annotations to the project
//@ThreadSafe
public class Parser {
    private final File file;

    public Parser(File file) {
        this.file = file;
    }


    //TODO without any knowledge on how this class is actually used, e.g. its load, it does not make sense to do anything more than synchronized
    public synchronized String getContent() throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = newBufferedInputStream()) {
            //TODO read by large chunks
            int data;
            while ((data = i.read()) != -1) {
                output.append((char) data);
            }
            return output.toString();
        }
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        StringBuilder output = new StringBuilder();
        try(BufferedInputStream i = newBufferedInputStream()) {
            int data;
            //TODO read by large chunks
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }

    private BufferedInputStream newBufferedInputStream() throws FileNotFoundException{
        return new BufferedInputStream(new FileInputStream(file));
    }

    private BufferedOutputStream newBufferedOutputStream() throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    public synchronized void saveContent(String content) throws IOException {
        try(BufferedOutputStream o = newBufferedOutputStream()) {
            o.write(content.getBytes());
        }
    }
}
