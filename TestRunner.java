import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by daniel on 13.01.2016.
 */
public class TestRunner {

    public static void main(String[] args) throws IOException {

        FileParser parser = new FileParser(
                new File("/tmp/smth-one.txt"),
                new FileReader(),
                new FileWriter()
        );
        System.out.println(parser.getContent());
        parser.saveContent(UUID.randomUUID().toString());
        System.out.println(parser.getContent());

        FileParser withoutUnicodeParser = new FileParser(
                new File("/tmp/smth-else.txt"),
                new WithoutUnicodeFileReader(),
                new FileWriter()
        );
        System.out.println(withoutUnicodeParser.getContent());
        withoutUnicodeParser.saveContent(UUID.randomUUID().toString());
        System.out.println(withoutUnicodeParser.getContent());
    }
}
