import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Parser class takes a text @see { java.io.File } as constructor argument and allows to read it as a String.
 * It also Allows to write a String literal to the file
 *
 */
public class Parser {

  private final File file;
  
  public Parser(File file){
	  this.file = file;
  }

  public File getFile() {
    return file;
  }

  /**
   * Convenience method that returns the String content of a text file including only Unicode characters in the ASCII range
   * @return String containing the value of the text file
   * @throws IOException
   */
  public String getContent() throws IOException {
	  return getContent(false);
  }

  /**
   * This method that reads a file as a text file, including the Unicode characters outside the ASCII range or not.
   * @param includeAscii Optional parameter that allows to include or exclude Unicode characters in the ASCII range in the result
   * @return String containing the value of the text file
   * @throws IOException
   */
  public String getContent(boolean includeAscii) throws IOException {
	  StringBuilder sb = new StringBuilder();
	  try(BufferedReader br = new BufferedReader(new FileReader(file))){
		  String line;
		  while ((line = br.readLine()) != null){
			  if (!includeAscii){
				  sb.append(line.chars().filter(c -> c > 0x80));
			  } else {
				  sb.append(line);
			  }
		  }
	  }
	  return sb.toString();
  }

  /**
   * Saves the provided String to the file
   * @param content The String to be saved to the file
   * @throws IOException
   */
  public void saveContent(String content) throws IOException {
	  try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
		  bw.write(content);
	  }
  }

}
