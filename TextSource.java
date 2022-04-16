import java.io.IOException;

public interface TextSource {

    String getContent() throws IOException;

    void saveContent(String content);
}
