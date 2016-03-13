import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser
{
    private final File file;

    public Parser(File file)
    {
        if (file == null)
            throw new IllegalArgumentException("file can not be null");
        this.file = file;
    }

    public String getContent()
    {
        return new Content(file).read();
    }

    public String getContentWithoutUnicode() throws IOException
    {
        return new ContentWithoutUnicode(file).read();
    }

    public void saveContent(String content) throws IOException
    {        
        //FIXME: Do the same steps in other methods including close outputstream, handle exception,...
        FileOutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1)
        {
            o.write(content.charAt(i));
        }
    }

    public interface Reader
    {
        String read();
    }

    public static class Content implements Reader
    {

        private final File f;

        public Content(File f)
        {
            this.f = f;
        }

        @Override
        public String read()
        {
            String output = "";
            FileInputStream i = null;
            try
            {
                i = new FileInputStream(f);
                int data;
                while ((data = i.read()) > 0)
                {
                    output += (char) data;
                }
                return output;
            }
            catch (IOException e)
            {
                // TODO: use log here
            }
            finally
            {
                closeQuietly(i);
            }
            return "";
        }

    }

    public static class ContentWithoutUnicode implements Reader
    {

        private final File f;

        public ContentWithoutUnicode(File f)
        {
            this.f = f;
        }

        @Override
        public String read()
        {
            String output = "";
            FileInputStream i = null;
            try
            {
                i = new FileInputStream(f);
                int data;
                while ((data = i.read()) > 0)
                {
                    if (data < 0x80)
                    {
                        output += (char) data;
                    }
                }
            }
            catch (IOException e)
            {
                // TODO: use log here
            }
            finally
            {
                closeQuietly(i);
            }
            return output;
        }

    }

    private static void closeQuietly(Closeable io)
    {
        try
        {
            if (io != null)
                io.close();
        }
        catch (Exception e)
        {
            // DO NOTHING
        }
    }

}
