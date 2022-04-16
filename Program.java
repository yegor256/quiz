import java.io.File;
import java.io.IOException;

public class Program implements Runnable {
    private final TextSource source;
    private final Storage storage;

    public Program(final String[] args) {
        final File destination = new File("test.txt");
        this.source = new UnicodeFreeTextSource(new FileTextSource(destination));
        this.storage = new FileTextStorage(destination, args[0]);
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
        new Program(args).run();
    }
}
