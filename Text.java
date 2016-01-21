import java.io.IOException;


public interface Text {

    /**
     * Read content from the source
     * @return String as read content
     * @throws java.io.IOException if something goes wrong
     */
    String read() throws IOException;

    /**
     * Write content to the source
     * @param content - Content which should be saved
     * @throws java.io.IOException if something goes wrong
     */
    void write(String content) throws IOException;

}
