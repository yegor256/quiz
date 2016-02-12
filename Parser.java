import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Predicate;

/**
 * This class is thread safe.
 */
public class Parser {
    private final File file;

    public Parser(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return getContent(i -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(i -> i < 0x80);
    }

    public void saveContent(String content) throws IOException {
        try (FileOutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
    }

    private String getContent(Predicate<Integer> filter) throws IOException {
        StringBuilder st = new StringBuilder();
        try (FileInputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test(data)) {
                    st.append(data);
                }
            }
        }

        return st.toString();
    }
}
