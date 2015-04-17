import java.io.*;
/**
 * This class is thread safe.
 */
public class Parser {

    private File file;

    private static final String encoding = "UTF-16";

    public Parser(File f) {
        this.file = file;
    }

    public StringBuffer getContent() {
        BufferedReader i = null;
        StringBuffer output = new StringBuffer("");
        String data;
        try {
            i = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), encoding));
            while ((data = i.readLine()) != null) {
                output.append(data);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (i != null)
                    i.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return output;
    }

    public StringBuffer getContentWithoutUnicode() throws IOException {
        FileInputStream i = null;
        StringBuffer output = new StringBuffer("");
        int data;
        try {
            i = new FileInputStream(file);
            while ((data = i.read()) != -1) {
                if (data < 0x80) {
                    output.append(Integer.toString(data));
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (i != null)
                    i.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return output;
    }

    public synchronized void saveContent(String content) throws IOException {
        Writer o = null;
        try {
            o = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file)));
            o.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
