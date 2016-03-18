import java.io.*;

/**
 * # Javadoc comments could be better, unit tests with multithreading file access should be added.
 * # Validate constructor file argument
 * @author Kresimir Popovic
 * @version 1.0.0
 * @since March 17, 2016
 */
public class Parser {

    private File file;

    /**
     * Better to set file instance into the constructor which will do basic file validation.
     * (synchronized setFile method not necessary any more)
     *
     * @param file file instance
     * @throws IllegalArgumentException thrown if file can't be read
     */
    public Parser(final File file) {
        this.validateConstructorArgument(file);
        this.file = file;
    }

    /**
     * @throws IllegalArgumentException thrown if file instance is doesn't
     */
    private void validateConstructorArgument(final File file) {
        if (file == null) {
            throw new IllegalArgumentException("File instance is null !");
        } else if (!file.exists()) {
            throw new IllegalArgumentException("File does not not exists !");
        } else if (!file.isFile()) {
            throw new IllegalArgumentException(
                String.format("This is not a file (%s), probably directory !", file.getAbsolutePath())
            );
        } else if (!file.canRead()) {
            throw new IllegalArgumentException(
                String.format("File (%s) can't be read !", file.getAbsolutePath())
            );
        }
    }

    /**
     * No synchronized keyword necessary any more.
     * @return currently loaded file instance into Parser instance.
     */
    public final File getFile() {
        return this.file;
    }

    /**
     * @return content with all characters
     */
    public final String getContent() throws IOException {
        return this.getContentInternal(0x256);
    }

    /**
     * When you look original getContent and getContentWithoutUnicode it can be noticed that they are almost the same.
     * We can write internal method which will be called by getContent and getContentWithoutUnicode.
     * <p>
     * Idea: Parser class could also be abstract class and getContentInternal can have protected access.
     * Then we could have 2 child classes: UnicodeContentParser and NonUnicodeContentParser
     * UnicodeContentParser - has getContent() public method which calls parent super.getContentInternal(0x80)
     * NonUnicodeContentParser - has getContent() public method which calls parent super.getContentInternal(0x256)
     * @return content
     * @throws IOException thrown in case of file read exception
     */
    private synchronized String getContentInternal(final int upperCharLimit) throws IOException {
        final StringBuilder output = new StringBuilder(10);
        // try - catch - resource (auto close)
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
            int data;
            while ((data = bufferedReader.read()) > 0) {
                if (data < upperCharLimit) {
                    output.append((char) data);
                }
            }
        } catch (final IOException ioe) {
            throw new IOException(
                String.format(
                    "Error while reading file (%s)",
                    this.file.getAbsolutePath()
                ),
                ioe
            );
        }

        return output.toString();
    }

    /**
     * @return content without unicode characters
     * @throws IOException
     */
    public final String getContentWithoutUnicode() throws IOException {
        return this.getContentInternal(0x80);
    }

    /**
     * Overwriting an input file is not normally done by something called a "parser".
     * -------------------------------------------------------------------------------
     * <p>
     * Add synchronized keyword if method will be called by multiple threads
     * because they will all try to write to the same file
     * @param content content to be written to the file (must be non null)
     */
    public final synchronized void saveContent(final String content) throws IOException {
        // try - catch - resource (auto close)
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.file))) {
            final char[] characters = content.toCharArray();
            for (final char chr : characters) {
                bufferedOutputStream.write(chr);
            }
        } catch (final IOException ioe) {
            throw new IOException(
                String.format(
                    "Error while writing to file (%s)",
                    this.file.getAbsolutePath()
                ),
                ioe
            );
        }
    }
}
