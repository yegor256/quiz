import java.io.IOException;

/**
 * Created by daniel on 13.01.2016.
 */
public interface Parser {

    public String getContent() throws IOException;

    public void saveContent(String content) throws IOException;
}
