import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  // 1. Buffer length in a separate constant. The length should be adjusted to a better length depending of the files opened.
  public static final int BUFFER_LENGTH = 16384;
    
  private File file;
  public synchronized void setFile(File f) {
    this.file = f;
  }
  public synchronized File getFile() {
    return this.file;
  }
  
  // 2. javadoc for public methods
  /**
   * Reads the content of a file and returns it as a string
   * 
   * @return the string content of the file
   * @throws IOException
   */
  public String getContent() throws IOException {
    // 3. Use a better name for the variable to have a more readable code.
    // 4. Use FileReader for reading Strings.
    // 5. Close the FileReader instance to avoid resource leaks. Use try{}finally{} or try(){} syntax.
    // 6. Use a synchronized access to the local file field.
    try (FileReader reader = new FileReader(getFile()))
    {
        // 7. Better to read data into a a char buffer, and not and int/char variable.
        char[] buffer = new char[Parser.BUFFER_LENGTH];
        // 8. Use a StringBuilder for collecting results (String concatenations are more efficient)
        StringBuilder result = new StringBuilder();
        for (int count = reader.read(buffer); count > -1; count = reader.read(buffer))
        {
            result.append(buffer, 0, count);
        }
        return result.toString();
    }
  }
  /**
   * Reads the content of a file and filter all unicode characters.
   * 
   * @return
   * @throws IOException
   */
  public String getContentWithoutUnicode() throws IOException {
    try (FileReader reader = new FileReader(getFile()))
    {
      char[] buffer = new char[Parser.BUFFER_LENGTH];
      StringBuilder result = new StringBuilder();
      for (int count = reader.read(buffer); count > -1; count = reader.read(buffer))
      {
        for (int index = 0; index < count; index++)
        {
          if (buffer[index] < 0x80)
          {
            // can have a solution of int lastIndex = 0; ... result.append(buffer, lastIndex, index); lastIndex = index; loop...
            result.append(buffer[index]);
          }
        }
      }
      return result.toString();
    }
  }
  /**
   * Save the content to the file
   * 
   * @param content The string content
   * @throws IOException
   */
  public void saveContent(String content) throws IOException {
    // 9. Use the FileWriter, try(), better variable name
    try (FileWriter writer = new FileWriter(getFile()))
    {
      writer.write(content);
    }
  }
}
