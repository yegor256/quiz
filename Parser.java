
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is thread safe.
 * <br>
 * This class is a utility class to write a string content to a file, or read
 * the string from a file.
 * 
 */
public class Parser {

    private File file;
    /**
     * This method updates the file to be read/written
     * @param file The file to read/write
     */
    public synchronized void setFile(File file) {
        this.file = file;
    }
    /**
     * This method updates the file to be read/written
     * 
     * @return 
     */
    public synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException, NullPointerException {
//        RENAME variable to a helpful name
//        FileInputStream fileInputStream = new FileInputStream(file);
//        String output = "";
//        int data;
//        while ((data = fileInputStream.read()) > 0) {
//            output += (char) data;
//        }
//        fileInputStream.close();//CLOSE STREAM
//        return output;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine())!=null){
            stringBuilder.append(line).append(System.getProperty("line.separator"));
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public String getContentWithoutUnicode() throws IOException, NullPointerException {
        //RENAME variable to a name that is helpful to another person
        //reading the code
        FileInputStream fileInputStream = new FileInputStream(file);
        //Use stringBuilder instead of direct string concatenation
        StringBuilder stringBuilder = new StringBuilder();
        
        int data;
        while ((data = fileInputStream.read()) > 0) {
            //unicode characters start at 20
            if (data>20 && data < 0x80) {
               stringBuilder.append((char) data);
            }
        }
        return stringBuilder.toString();
    }
    /**
     * This method writes to the selected file
     * @param content the String content to write to the file
     * @throws java.io.IOException
     */
    public synchronized void saveContent(String content) throws IOException, NullPointerException {
//        //Rename variable to a name helpful to team mates
//        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        for (int i = 0; i < content.length(); i += 1) {
//            fileOutputStream.write(content.charAt(i));
//        }
//        //close stream
//        fileOutputStream.close();
        
        //since we are working with a String and not binary data,
        //we use resources created specifically for that purpose
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    }
}
