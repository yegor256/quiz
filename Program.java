import java.io.File;
import java.io.IOException;

public class Program implements Runnable {
    private final String[] args;
    private final TextSource source;

    public Program(final String[] args) {
        this.args = args;
        this.source =
                new UnicodeFreeTextSource(
                        new TextFile(
                                new File("test.txt")
                        )
                );
    }

    @Override
    public void run() {
        source.saveContent(this.args[0]);
        try {
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
