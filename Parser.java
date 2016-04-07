import java.io.*;

/**
 * Generic interface for a Parser.
 */
interface Parser<T> {

    T getContent() throws IOException;

    void setContent(T content) throws IOException;
}

/**
 * Abstract parser decorator
 */
abstract class ParserDecorator<T> implements Parser<T> {
    final private Parser<T> decoratedParser;

    public ParserDecorator(Parser<T> p) {
        this.decoratedParser = p;
    }

    @Override
    public T getContent() throws IOException {
        return this.decoratedParser.getContent();
    }

    @Override
    public void setContent(T content) throws IOException {
        this.decoratedParser.setContent(content);
    }
}

/**
 * Parser that reads/writes text (using default format - UTF-8) from/to a File.
 * This class is thread safe.
 */
class TextFileParser implements Parser<String> {

    final private File file;

    public TextFileParser(File f) {
        this.file = f;
    }

    public File getFile() {
        return this.file;
    }

    synchronized public String getContent() throws IOException {
        BufferedReader reader = null;
        try {
            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(new FileReader(this.getFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    synchronized public void setContent(String content) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(this.getFile()));
            writer.write(content);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}

/**
 * Parser decorator that guarantees that content only contains ascii text.
 */
class AsciiParser extends ParserDecorator<String> {

    public AsciiParser(Parser<String> p) {
        super(p);
    }

    @Override
    public String getContent() throws IOException {
        return filterUnicode(super.getContent());
    }

    @Override
    public void setContent(String content) throws IOException {
        super.setContent(filterUnicode(content));
    }

    private String filterUnicode(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < 0x80) {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
