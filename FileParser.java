import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * This class is thread safe.
 * <p>
 * <p>
 * <code>
 * Parser parser = Parser
 * .newBuilder()
 * .excludeUnicode(false)
 * .build();
 * <p>
 * parser.getContent(file);
 * <p>
 * </code>
 * String content = parserBuilder.getContent(file);
 *
 * The signature of methods remained the same.
 */
public class FileParser {

    private final Charset charset;

    private final boolean excludeUnicode;

    private FileParser(ParserBuilder builder) {
        charset = builder.character;
        excludeUnicode = builder.excludeUnicode;
    }

    public static ParserBuilder newBuilder() {
        return new ParserBuilder();
    }


    public String getContent(File file) throws IOException {

        if (!file.exists()) {
            throw new IOException("File not found");
        }

        byte[] bytes = Files.readAllBytes(file.toPath());

        if (!excludeUnicode) {
            return new String(bytes, charset);
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            if ((b & 0x80) == 0) {
                sb.append((char) b);
            }
        }

        return sb.toString();
    }

    /**
     * Builder create thread safe parser.
     */
    public static final class ParserBuilder {

        private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

        private Charset character;
        private boolean excludeUnicode;

        private ParserBuilder() {
        }

        public ParserBuilder character(Charset character) {
            this.character = character;
            return this;
        }

        public ParserBuilder excludeUnicode(boolean excludeUnicode) {
            this.excludeUnicode = excludeUnicode;
            return this;
        }

        public FileParser build() {
            if (character == null) {
                character = DEFAULT_CHARSET;
            }
            return new FileParser(this);
        }
    }
}
