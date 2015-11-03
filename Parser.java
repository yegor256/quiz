import java.io.*;
import java.lang.StringBuilder;

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
        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader(file)); //User a BufferedReader instead of reading from FileInputStream
            StringBuilder output = new StringBuilder(); //String concatenation replaced with StringBuilder
            String line;
            while ((line = r.readLine()) != null) {
                output.append(line);
            }
            return output.toString();
        } finally {
            if (r != null) {
                r.close(); //Close the BufferedReader
            }
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        FileInputStream i = null;
        try {
            i = new FileInputStream(file);
            StringBuilder output = new StringBuilder(); //String concatenation replaced with StringBuilder
            int data;
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
            return output.toString();
        } finally {
            if (i != null) {
                i.close(); //Close the input stream
            }
        }
    }

    public void saveContent(String content) throws IOException {
        BufferedWriter br= null;
        try {
            br = new BufferedWriter(new FileWriter(file)); //Use a buffered writer instead of writing directly to output stream
            br.write(content);
        } finally {
            if (br != null) {
                br.close(); //Close BufferedReader
            }
        }
    }
}
