import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Parser {

    private final File file;

    public Parser(String filePath) {
        this.file = new File(filePath);
    }

    public synchronized String getContent(Filter filter) {
        StringBuilder output = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file)) {
            int data;
            while ((data = fis.read()) != -1) {
                if (filter.apply(data)) {
                    output.append((char) data);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found " +  file.getAbsolutePath(), e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read content from " + file.getAbsolutePath(), e);
        }
        return output.toString();
    }

    public synchronized void saveContent(String content) {
        if (content == null || content.length() == 0) {
            throw new IllegalArgumentException("Invalid content");
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos.write(content.getBytes());
            fos.flush();
        } catch (IOException e) {
            throw new RuntimeException("Unable to write content to" + file.getAbsolutePath(), e);
        }
    }

    public File getFile() {
        return file;
    }

    public interface Filter {
        boolean apply(int data);
    }

    public class WithoutUnicodeFilter implements Filter {

        @Override
        public boolean apply(int data) {
            return data < 0x80;
        }
    }

    public class NoFilter implements Filter {

        @Override
        public boolean apply(int data) {
            return true;
        }
    }

}
