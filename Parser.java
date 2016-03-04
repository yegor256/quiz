/**
 * Re-factored Parser class
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Parser class: used to read and write to a given file, filtering Unicode characters on
 * input if requested. This class is thread safe.
 */
public class Parser {
    private File file;
  
    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    /**
    * Return content from file, including Unicode characters.
    */
    public String getContent() throws IOException {
        return getContent(true);
    }
  
    /**
    * Return content from file, stripping/dropping Unicode characters.
    */
    public String getContentWithoutUnicode() throws IOException {
        return getContent(false);
    }

    /**
    * Common shared method to read from a file, with a flag to indicate whether to include Unicode
    * characters or not.
    *
    * @param withUnicode Should Unicode characters be included in the output?
    * @return Returns String containing contents of file.
    */
    private String getContent(Boolean withUnicode) throws IOException {
        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;

        while ((data = i.read()) > 0) {
            // if we don't want Unicode values, do not add them to the output
            if (withUnicode == false && data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }

    /**
    * Method to save a string to a file.
    *
    * @param content The data that should be saved to the file.
    */
    public void saveContent(String content) throws IOException {
        FileOutputStream o = new FileOutputStream(file);

        // there was a for loop here. replaced with something more concise
        o.write(content.getBytes());
    }
}
