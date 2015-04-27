import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
    final private File file;

    public Parser(String filename) throws FileNotFoundException {
        this.file = new File(filename);
    }


    public synchronized String getContent() throws IOException {
        try (final FileInputStream i = new FileInputStream(file)) {
            StringBuilder output = new StringBuilder();
            int data;
            byte buf[] = new byte[1024];
            while ((data = i.read(buf)) > 0) {
                output.append(buf);
            }
            return output;
        }
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        try(final FileInputStream i = new FileInputStream(file)) {
            StringBuilder readBuffer = new StringBuilder();
            int data;
            byte buf[] = new byte[1024];
            while ((data = i.read(buf)) > 0) {
                readBuffer.append(buf);
            }

            StringBuilder output = new StringBuilder();
            for (int index=0; index < readBuffer.length(); index++) {
                if(readBuffer.charAt(index) < 0x80) {
                    output.append(readBuffer.charAt(index));
                }
            }
            return output.toString();
        }
    }
    public synchronized void saveContent(String content) throws IOException {
        try (final FileOutputStream out = new FileOutputStream(file)) {
            out.write(content.getBytes());
        }

    }
}
