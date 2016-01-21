import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class FileText implements Text {

    private final File source;

    public FileText(final File file) {
        this.source = file;
    }

    @Override
    public synchronized String read() throws IOException {
        final FileInputStream input = new FileInputStream(this.source);
        final StringBuilder output = new StringBuilder("");
        int data;
        while ((data = input.read()) > 0) {
            output.append((char) data);
        }
        return output.toString();
    }

    @Override
    public synchronized void write(final String content) throws IOException {
        final FileOutputStream output = new FileOutputStream(this.source);
        for (int count = 0; count < content.length(); count += 1) {
            output.write(content.charAt(count));
        }
    }
}
