package parser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * This class is thread safe.
 */
public class Parser implements java.io.Serializable {
    private File file;
    private BufferedReader br = null;
    private String line = null;
    public synchronized void setFile(File f) {
        file = f;
    }
    public synchronized File getFile() {
        return file;
    }
    public void getContent() throws IOException {
        br = new BufferedReader(new FileReader(file));
        readLine(br);
    }
    public void getContentWithoutUnicode() throws IOException {
        br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ASCII"));
        readLine(br);
    }
    public void saveContent(String content) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
        bw.write(content);
        bw.close();
    }
    public void readLine(BufferedReader br) throws IOException {
        line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }
}
