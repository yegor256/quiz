package ch.fware.teamed.quiz;

import java.io.File;
import java.io.IOException;

public interface FileWriter {

    void write(File file, String stringToWrite) throws IOException;
}