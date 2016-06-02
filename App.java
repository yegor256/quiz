import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        TextFile textFile = new DefaultTextFile(
                new File("test")
        );

        textFile.write("ABCĆ");
        System.out.println(textFile.read());

        NoUnicodeTextFile noUnicodeTextFile = new NoUnicodeTextFile(textFile);
        System.out.println(noUnicodeTextFile.read());
    }
}
