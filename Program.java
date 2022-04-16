import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Program implements Runnable {
    private final TextSource source;
    private final Storage storage;

    public Program(final TextSource source, final Storage storage) {
        this.source = source;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            this.storage.save();
            final String result = source.getContent();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final File destination = new File("test.txt");
        new Program(
                new UnicodeFreeTextSource(
                        new FileTextSource(
                                destination
                        )
                ),
                new FileTextStorage(
                        destination,
                        () -> new Scanner(System.in).nextLine()
                )
        ).run();
    }
}
