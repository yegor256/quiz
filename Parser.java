import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

	private File file;

	/**
	 * lock object for the file.
	 */
	private Object filelock = new Object();

	/**
	 * Set the file object
	 * 
	 * @param file
	 */
	public void setFile( File file ) {
		synchronized ( filelock ) {
			this.file = file;
		}
	}

	/**
	 * Get the file instance. I am not sure why this object is exposed to outside.
	 * 
	 * @return
	 */
	public synchronized File getFile() {
		return file;
	}

	/**
	 * get the content of the file.
	 * 
	 * @return the content of the file
	 * @throws IOException
	 */
	public String getContent() throws IOException {
		StringBuilder output = new StringBuilder();
		synchronized ( filelock ) {
			BufferedReader reader = new BufferedReader( new FileReader( file ) );

			String strLine;
			while ( (strLine = reader.readLine()) != null ) {
				output.append( strLine );
				output.append( "\n" );
			}
			reader.close();
		}
		return output.toString();
	}

	/**
	 * Requirements are not clear to me. I assume, the requirement is to remove any non-ASCII
	 * characters. I see you check in the sample code to check the integer less than 128, which I
	 * don't understand, it should be 256:)
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getContentWithoutUnicode() throws IOException {
		StringBuilder output = new StringBuilder();
		synchronized ( filelock ) {
			BufferedReader reader = new BufferedReader( new FileReader( file ) );

			String strLine;
			while ( (strLine = reader.readLine()) != null ) {
				strLine = strLine.replaceAll( "\\P{Print}", "" ); // regex: all characters that are not ASCII are replaced with the empty string
				output.append( strLine );
				output.append( "\n" );
			}
			reader.close();
		}
		return output.toString();
	}

	/**
	 * Write to the file.
	 * 
	 * @param content
	 * @throws IOException
	 */
	public void saveContent( String content ) throws IOException {
		synchronized ( filelock ) {
			BufferedWriter writer = new BufferedWriter( new FileWriter( file ) );
			writer.write( content );
			writer.close();
		}
	}
}
