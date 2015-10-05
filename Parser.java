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

    public synchronized String getContent() throws IOException {
        InputStream in = null;
        StringBuilder outputString = new StringBuilder();
        try {
            if (file == null) throw new FileNotFoundException();
            in = new BufferedInputStream(new FileInputStream(file));
            int data;
            while ((data = in.read()) != -1) {
                outputString.append((char) data);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return outputString.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        InputStream in = null;
        StringBuilder outputString = new StringBuilder();
        try {
            if (file == null) throw new FileNotFoundException();
            in = new BufferedInputStream(new FileInputStream(file));
            int data;
            while ((data = in.read()) != -1) {
                if (data < 0x80) {
                    outputString.append((char) data);
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return outputString.toString();
    }

    public synchronized void saveContent(String content) throws IOException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            for (int i = 0; i < content.length(); i++) {
                out.write(content.charAt(i));
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
