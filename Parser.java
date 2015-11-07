import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * <p>
 * This class is used to read file content to string (optionally without unicode
 * characters) and replace file content by string value. This class is thread safe.
 * </p>
 * <b>NOTE!</b> File size is limited up to 100Mb
 */
public class Parser
{
	private final File file;

	public Parser(File file)
	{
		if (!file.exists())
			throw new IllegalArgumentException("Parameter `file` must point to already created file");
		if (file.isDirectory())
			throw new IllegalArgumentException("Parameter `file` must point to file, not to directory");
		if (file.length() / 1048576 > 100)
			throw new IllegalArgumentException("Parameter `file` must point to file that not exceeds 100Mb size");

		this.file = file;
	}

	public String getContent() throws IOException
	{
		synchronized (file)
		{
			byte[] encoded = Files.readAllBytes(Paths.get(file.toURI()));
			return new String(encoded);
		}
	}

	public String getContentWithoutUnicode() throws IOException
	{
		synchronized (file)
		{
			BufferedInputStream f = new BufferedInputStream(new FileInputStream(file));
			StringBuilder output = new StringBuilder();
			int data = 0;
			while ((data = f.read()) != -1)
				if (data < 0x80)
					output.append((char) data);
			f.close();
			return output.toString();
		}
	}

	public void saveContent(String content) throws IOException
	{
		synchronized (file)
		{
			Files.write(Paths.get(file.toURI()), content.getBytes(), StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		}
	}
}
