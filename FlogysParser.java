package ch.fware.teamed.quiz;

import java.io.File;
import java.io.IOException;

public class FlogysParser implements Parser {
    private final File file;
    private FileReader fileReader;
    private FileWriter fileWriter;

    public FlogysParser(File file, FileReader fileReader, FileWriter fileWriter) {
        this.file = file;
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
    }

    public String getContent() throws IOException {
        return fileReader.readAndFilterContent(file, new NoFilter());
    }

    public String getContentWithoutUnicode() throws IOException {
        return fileReader.readAndFilterContent(file, new RemoveUnicodeFilter());
    }

    public void saveContent(String content) throws IOException {
        fileWriter.write(file, content);
    }
}