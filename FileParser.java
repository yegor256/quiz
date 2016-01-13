import java.io.File;
import java.io.IOException;

/**
 * Created by daniel on 13.01.2016.
 */
public class FileParser implements Parser {

    private final File file;

    private Reader fileReader;
    private Writer fileWriter;

    public FileParser(File file, Reader fileReader, Writer fileWriter) throws IOException {
        //TODO: maybe add some null-check
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
        this.file = file;
        if (!file.exists()) {       // or any other approach, e.g throwing exception if not exists
            file.createNewFile();
        }
    }

    @Override
    public String getContent() throws IOException {
        String content;
        synchronized (this) {       // to avoid dirty-read
            content = fileReader.read(file);
        }
        return content;
    }

    @Override
    public void saveContent(String content) throws IOException {
        synchronized (this) {   // only one thread writes to the file at the moment
            fileWriter.write(file, content);
        }
    }

}
