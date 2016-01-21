package teamed.io.quiz;

import java.io.*;
/**
 * This class is thread safe.
 */
public class Parser {
    private final File file;

    public Parser(File file) {
        this.file = file;
    }


    public File getFile() {
        return file;
    }

    public String getContent(boolean withUnicode) throws IOException {

        synchronized (file) {
            try (FileInputStream i = new FileInputStream(file)){
                StringBuilder output = new StringBuilder();
                int data;

                while((data = i.read()) > 0 ) {
                    if(withUnicode) {
                        output.append((char) data);
                    } else {
                        if (data < 0x80) {
                            output.append((char) data);
                        }
                    }
                }

                return output.toString();
            }
        }
    }

    public void saveContent(String content) throws IOException {

        synchronized (file) {
            try (FileOutputStream o = new FileOutputStream(file)) {
                for (int i = 0; i < content.length(); i += 1) {
                    o.write(content.charAt(i));
                }
            }
        }
    }
}