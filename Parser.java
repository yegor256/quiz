import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;

    public void setFile(File f) {
        synchronized (file) {
            file = f;
        }
    }

    public synchronized File getFile() {
        synchronized (file) {
            return file;
        }
    }

    public String getContent() throws IOException {
        if (file == null || !file.exists() || !file.isFile()) { // check file is valid before init resources
            System.out.println("File is invalid !");
            throw new FileNotFoundException("File is invalid !");
        }
        String output = "";
        FileInputStream i = null;
        try {
            synchronized (file) {
                i = new FileInputStream(file);
                int data;
                while ((data = i.read()) > 0) {
                    output += (char) data;
                }
            }
        } catch (IOException ex) {
            System.out.println("There is exception when get content from file " + file.getName());
            throw new IOException(ex);
        } finally {
            if (i != null) {
                i.close();
            }
        }
        return output;
    }

    public String getContentWithoutUnicode() throws IOException {
        if (file == null || !file.exists() || !file.isFile()) {  // check file is valid before init resources
            System.out.println("File is invalid !");
            throw new FileNotFoundException("File is invalid !");
        }
        String output = "";
        FileInputStream i = null;
        try {
            synchronized (file) {
                i = new FileInputStream(file);
                int data;
                while ((data = i.read()) > 0) {
                    if (data < 0x80) {
                        output += (char) data;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("There is exception when get content without unicode from file " + file.getName());
            throw new IOException(ex);
        } finally {
            if (i != null) {
                i.close();
            }
        }
        return output;
    }

    public void saveContent(String content) throws Exception {
        if (content == null || content.isEmpty()) { // check content before init resources
            System.out.println("Content is invalid");
            throw new IllegalArgumentException("Content is invalid");
        }

        if (file == null || !file.isFile()) { // check file is valid before init resources
            System.out.println("File is invalid !");
            throw new FileNotFoundException("File is invalid !");
        }

        FileOutputStream o = null;
        try {
            synchronized (file) {
                o = new FileOutputStream(file);
                for (int i = 0; i < content.length(); i += 1) {
                    o.write(content.charAt(i));
                }
            }
        } catch (IOException ex) {
            System.out.println("There is exception when save content to file " + file.getName());
            throw new IOException(ex);
        } finally {
            if (o != null) {
                o.close();
            }
        }
    }
}
