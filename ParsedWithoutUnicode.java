import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class ParsedWithoutUnicode implements Content<String> {
    private final File file;

    public ParsedWithoutUnicode(File file) {
        this.file = file;
    }

    @Override
    public String get() {
        try {
            FileInputStream i = new FileInputStream(file);
            String output = "";
            int data;
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
            return output;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File not found: [%s]", file.getAbsolutePath()), e);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to read file: [%s]", file.getAbsolutePath()), e);
        }
    }
}
