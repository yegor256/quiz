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
        return getContent(true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return getContent(false);
    }

    public synchronized void saveContent(String content) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(content);
        fw.flush();
        fw.close();
    }

    private String getContent(boolean includeUnicode) throws IOException {
        FileInputStream input = new FileInputStream(file);
        StringBuilder output = new StringBuilder();

        byte[] data = new byte[1024];
        int read = input.read(data);

        while (read > 0) {

            if (!includeUnicode) {
                byte[] newData = new byte[read];

                int newRead = 0;
                for (int i = 0; i < read; i++) {
                    if (data[i] < 0x80) {
                        newData[newRead++] = data[i];
                    }
                }

                data = newData;
                read = newRead;
            }

            output.append(new String(data, 0, read));
            read = input.read(data);
        }

        input.close();
        return output.toString();
    }
}
