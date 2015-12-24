import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class Parser
{
    private File file;

    public synchronized void setFile(File f) { file = f; }

    public synchronized File getFile() { return file; }

    public synchronized String getContent() throws IOException
    {
        StringBuilder fileContents = new StringBuilder((int) file.length());
        String lineSeparator = System.getProperty("line.separator");

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file))))
        {
            if (scanner.hasNextLine())
            {
                fileContents.append(scanner.nextLine());
            }
            while (scanner.hasNextLine())
            {
                fileContents.append(lineSeparator + scanner.nextLine());
            }
            return fileContents.toString();
        }
    }

    public synchronized String getContentWithoutUnicode() throws IOException
    {
        StringBuilder fileContents = new StringBuilder((int) file.length());

        for (byte b : Files.readAllBytes(file.toPath()))
        {
            fileContents.append(b);
        }

        return fileContents.toString();
    }

    public synchronized void saveContent(String content) throws IOException
    {
        Files.write(file.toPath(), content.getBytes());
    }
}
