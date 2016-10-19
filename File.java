import java.io.IOException;

public interface File {
    /**
     * Get content of the file as string.
     * 
     * @param ignoreUnicodeCharacters
     *            ignore Unicode characters
     * @return File content as a string.
     * @throws IOException
     *             if the file not exists or an I/O error occurs.
     */
    String getContent(boolean ignoreUnicodeCharacters) throws IOException;

    /**
     * Write content to file.
     * 
     * @param content
     *            content to be written.
     * @throws IOException
     *             if the file exists but is a directory rather than a regular
     *             file, does not exist but cannot be created, or cannot be
     *             opened for any other reason or an I/O error occurs.
     */
    void saveContent(String content) throws IOException;
}
