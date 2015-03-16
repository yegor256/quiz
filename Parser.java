import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class is thread safe.
 */
public class Parser
{
    private final File file;

    private Parser(File file) //make it immutable. In this case we don't need to worry about thread safe
    {
        this.file = file;
    }

    public static Parser create(File file) //i prefer factory method to public constructor because it's more flexible
    {
        return new Parser(file);
    }

    public File getFile()
    {
        return file;
    }

    public String getContent() throws IOException
    {
        return new String(Files.readAllBytes(file.toPath()));
    }

    public String getContentWithoutUnicode() throws IOException  //todo this method is not clear. Should it read only ASCII symbol or what does it mean "without unicode"?
    {
        FileInputStream i = new FileInputStream(file);
        StringBuilder output = new StringBuilder(); //replaced with StringBuilder
        int data;
        while ((data = i.read()) > 0)
        {
            if (data < 0x80)
            {
                output.append((char) data);
            }
        }

        i.close(); //close stream
        return output.toString();
    }

    public synchronized void saveContent(String content) throws IOException  //we can't write to file simultaneously
    {
        Files.write(file.toPath(), content.getBytes());
    }
}
