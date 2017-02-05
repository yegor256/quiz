package ch.fware.teamed.quiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReaderImpl implements FileReader {

    @Override
    public String readAndFilterContent(File fileToRead, DataFilter dataFilter) throws IOException {
        FileInputStream i = new FileInputStream(fileToRead);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (dataFilter.isAllowedData(data)) {
                output += (char) data;
            }
        }
        return output;
    }
}