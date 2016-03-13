import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author omidp
 *
 */
public class Parser
{

    private final static Logger LOGGER = Logger.getLogger(Parser.class.getName());

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

    public String getContentWithoutUnicode()
    {
        return new ContentWithoutUnicode(file).read();
    }

    public void saveContent(final String content)
    {
        new Writer() {
            @Override
            public void write()
            {
                if (content == null || content.trim().length() == 0)
                    throw new IllegalArgumentException("content can not be empty");
                FileOutputStream outputStream = null;
                try
                {
                    outputStream = new FileOutputStream(file);
                    outputStream.write(content.getBytes());
                }
                catch (IOException e)
                {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
                finally
                {
                    closeQuietly(outputStream);
                }
            }
        }.write();

    }

    public interface Writer
    {
        void write();
    }

    public interface Reader
    {
        String read();
    }

    /**
     * @author omidp
     *         <p>
     *         also read unicode
     *         </p>
     */
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
            InputStream i = null;
            try
            {
                i = new FileInputStream(f);
                BufferedReader in = new BufferedReader(new InputStreamReader(i, "UTF-8"));
                String readLine;
                while ((readLine = in.readLine()) != null)
                {
                    output += readLine;
                }
                return new String(output.getBytes(), "UTF-8");
            }
            catch (IOException e)
            {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
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
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
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
