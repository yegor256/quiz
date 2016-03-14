import java.io.IOException;

public interface ContentHandler {

    String getContent(boolean filterUnicode);

    void saveContent(String content);

}
