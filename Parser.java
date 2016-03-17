import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author OmidPourhadi [AT] gmail [DOT] com
 *
 */
public final class Parser
{

    // Immutability
    private final File file;

    private final ParserDecorator parserDecorator;

    public Parser(File file, ParserDecorator parserDecorator)
    {
        // don't write code here
        this.file = file;
        this.parserDecorator = parserDecorator;
    }

    // catch-me exception
    public String parse() throws IOException
    {
        // fail-fast
        if (file == null)
            throw new IllegalArgumentException("file can not be null");
        if (parserDecorator == null)
            throw new IllegalArgumentException("parserDecorator can not be null");
        return parserDecorator.parse(file);
    }

    public void saveContent(String content) throws IOException
    {
        Path f = Paths.get(this.file.toURI());
        Files.write(f, new Words(content), Charset.forName("UTF-8"));
    }

    public interface ParserDecorator
    {
        public String parse(File file) throws UnsupportedEncodingException, IOException;
    }

    public static class Content implements ParserDecorator
    {
        @Override
        public String parse(File file) throws UnsupportedEncodingException, IOException
        {
            // Null is bad
            String output = "";
            Words words = new Words(new Text(file).toString());
            for (String item : words)
            {
                output += item;
            }
            return output;
        }
    }

    public static class ContentWithoutUnicode implements ParserDecorator
    {

        @Override
        public String parse(File file) throws UnsupportedEncodingException, IOException
        {
            // Null is bad
            String output = "";
            Words words = new Words(new Text(file).toString());
            for (String item : words)
            {
                if (new BigInteger(1, item.getBytes()).intValue() < 0x80)
                {
                    output += item;
                }
            }
            return output;
        }

    }

    // Object Thinking
    private static class Words implements Iterable<String>
    {

        private String texts;

        Words(String texts)
        {
            this.texts = texts;
        }

        @Override
        public Iterator<String> iterator()
        {
            List<String> words = new ArrayList<String>();
            for (int i = 0; i < texts.length(); i++)
            {
                char c = texts.charAt(i);
                words.add(Character.toString(c));
            }
            return words.iterator();
        }

    }

    static class Text
    {
        private final File f;

        Text(File f)
        {
            this.f = f;
        }

        @Override
        public String toString()
        {
            try
            {
                return new String(Files.readAllBytes(Paths.get(f.toURI())), "UTF-8");
            }
            catch (IOException e)
            {
                throw new IllegalArgumentException("can not read file content", e);
            }
        }
    }

}
