package ch.fware.teamed.quiz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriterImpl implements FileWriter {

    @Override
    public void write(File file, String stringToWrite) throws IOException {
        FileOutputStream o = new FileOutputStream(file);
        for (int i = 0; i < stringToWrite.length(); i ++) {
            o.write(stringToWrite.charAt(i));
        }
    }
}