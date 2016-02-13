import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser
{
	private File file;
	
	public synchronized void setFile(File file)
	{
		this.file = file;
	}

	public synchronized File getFile()
	{
		return file;
	}
	
	public String getContent() throws IOException
	{
		FileInputStream stream = new FileInputStream(file);
		String output = "";
		int data;
		while((data = stream.read()) > 0)
		{
			output += (char)data;
		}
		return output;
	}
	
	public String getContentWithoutUnicode() throws IOException
	{
		FileInputStream stream = new FileInputStream(file);
		String output = "";
		int data;
		while ((data = stream.read()) > 0)
		{
			if (data < 0x80)
			{
				output += (char) data;
			}
		}
		return output;
	}
	
	public void saveContent(String content) throws IOException
	{
		FileOutputStream stream = new FileOutputStream(file);
		for (int i = 0, len = content.length(); i < len; i++)
		{
			stream.write(content.charAt(i));
		}
	}
}