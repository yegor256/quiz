import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

    private File file;

    public void setFile(File f) {
        file = f;
    }

    public File getFile() {
        return file;
    }

    public String getContent(boolean hasUnicode) throws IOException {
        BufferedReader reader = hasUnicode ? new BufferedReader(new FileReader(file)) :
                                    new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        try {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
                output.append('\n');
            }
            return output.toString();
        }
        finally {
            reader.close();
        }
    }

    public String getContent() throws IOException {
        return getContent(true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(false);
    }

    public void saveContent(String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            writer.write(content);
        }
        finally {
            writer.close();
        }
    }
}