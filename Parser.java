import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class FileEditor {
    private File file;
    private Object syncObject = new Object();
    public void setFile(File f) {
        synchronized (syncObject) {
            file = f;
        }
    }
    public File getFile() {
        synchronized (syncObject){
            return file;
        }
    }
    public String getFileContent(boolean isInUnicode) throws IOException {
        synchronized (syncObject) {
            StringBuilder stringBuilder = new StringBuilder();
            FileInputStream i = new FileInputStream(file);
            String output = "";
            int data;
            while ((data = i.read()) > 0) {
                if (isInUnicode && data < 0x80) {
                    stringBuilder.append((char) data);
                } else {
                    stringBuilder.append((char) data);
                }
            }
            return stringBuilder.toString();
        }
    }
    public void writeToFile(String str) throws IOException {
        synchronized (syncObject) {
            FileOutputStream out = new FileOutputStream(file);
            for (int i = 0; i < str.length(); i += 1) {
                out.write(str.charAt(i));
            }
        }
    }
}
