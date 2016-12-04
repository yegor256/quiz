import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
    private volatile File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }
    // it will be better to use IOUtils.readAsString(file) and IOUtils.readAsString(file).filter(StringUtils::isUnicode)
    public synchronized String getContent(boolean withoutUnicode) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (!withoutUnicode || (data < 0x80)) {  // do not understand why we need to read without unicode, 
                    stringBuilder.append(data);         // file content will be broken  
                }
            }
        }
        return stringBuilder.toString();
    }
    // it will be better to use IOUtils.writeStringToFile(file)
    public synchronized void saveContent(String content) throws IOException {
        try (FileOutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
    }
}
