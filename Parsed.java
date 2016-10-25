import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class Parsed implements Content<String> {
    private final File file;

    public Parsed(File file) {
        this.file = file;
    }

    public String get() {
        try {
            FileInputStream i = new FileInputStream(file);
            String output = "";
            int data;
            while ((data = i.read()) > 0) {
                output += (char) data;
            }
            return output;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File not found: [%s]", file.getAbsolutePath()), e);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to read file: [%s]", file.getAbsolutePath()), e);
        }
    }


}
