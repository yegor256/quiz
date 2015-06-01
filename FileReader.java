package ch.fware.teamed.quiz;

import java.io.File;
import java.io.IOException;

public interface FileReader {

    String readAndFilterContent(File fileToRead, DataFilter dataFilter) throws IOException;
}