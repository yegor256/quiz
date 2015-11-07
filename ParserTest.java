import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

public class ParserTest
{
	private final String someText = "Some simple text";
	private final String unicodeText = "日本語japaneeseРусскийrussian中國chineese";
	private final String deunicodedText = "japaneeserussianchineese";

	@Before
	public void setUp() throws IOException
	{
		createFile("simple.txt", someText);
		createFile("unicode.txt", unicodeText);
		// createFile("unicode.txt", StringEscapeUtils.escapeJava("日本語"));
		File file = createFile("noaccess.txt", "This file is not accessible");
		file.setReadable(false);
		file.setWritable(false);
		File dir = new File("./directory/simple.txt");
		dir.mkdirs();
	}

	private File createFile(String fileName, String content) throws IOException, FileNotFoundException
	{
		File file = new File(fileName);
		if (file.exists())
		{
			file.delete();
		}
		file.createNewFile();
		PrintWriter pw = new PrintWriter(file);
		pw.print(content);
		pw.close();
		return file;
	}

	@Test
	public void testGetContent() throws IOException
	{
		Parser parser = new Parser(new File("simple.txt"));
		assertEquals(someText, parser.getContent());
	}

	@Test
	public void testGetContentWithoutUnicode() throws IOException
	{
		Parser parser = new Parser(new File("unicode.txt"));
		assertEquals(deunicodedText, parser.getContentWithoutUnicode());
	}

	@Test
	public void testSetContent() throws IOException
	{
		Parser parser = new Parser(new File("simple.txt"));
		parser.saveContent(unicodeText);
		assertEquals(unicodeText, parser.getContent());
		assertEquals(deunicodedText, parser.getContentWithoutUnicode());
	}

	@Test
	public void testThreadSafe() throws InterruptedException, ExecutionException, FileNotFoundException, IOException
	{
		createFile("threads.txt", "");
		ExecutorService ex = Executors.newCachedThreadPool();
		Parser parser = new Parser(new File("threads.txt"));
		List<Future<String>> futures = new ArrayList<Future<String>>();
		for (int i = 0; i < 1000; i++)
		{
			 futures.add(i, ex.submit(new ParserThread(parser, false)));
		}
		for (int i = 0; i < 1000; i++)
		{
			int fileLength = futures.get(i).get().length();
			// File cointains one and only one UUID (36 symbols) 
			assertEquals(36, fileLength);
		}
	}
	
	@Test
	public void testThreadSafeDeunicoded() throws InterruptedException, ExecutionException, FileNotFoundException, IOException
	{
		createFile("threads.txt", "");
		ExecutorService ex = Executors.newCachedThreadPool();
		Parser parser = new Parser(new File("threads.txt"));
		List<Future<String>> futures = new ArrayList<Future<String>>();
		for (int i = 0; i < 1000; i++)
		{
			 futures.add(i, ex.submit(new ParserThread(parser, true)));
		}
		for (int i = 0; i < 1000; i++)
		{
			int fileLength = futures.get(i).get().length();
			// File cointains one and only one UUID (36 symbols) 
			assertEquals(36, fileLength);
		}
	}

	@Test(expected = AccessDeniedException.class)
	public void testNoReadRights() throws IOException
	{
		Parser parser = new Parser(new File("noaccess.txt"));
		parser.getContent();
	}

	@Test(expected = AccessDeniedException.class)
	public void testNoWriteRights() throws IOException
	{
		Parser parser = new Parser(new File("noaccess.txt"));
		parser.saveContent("Some content");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNoFile() throws IOException
	{
		new Parser(new File("nosuchfile.txt"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsDirectory() throws IOException
	{
		new Parser(new File("directory"));
	}

	private class ParserThread implements Callable<String>
	{
		private Parser parser;
		private boolean deunicoded;

		public ParserThread(Parser parser, boolean deunicoded)
		{
			this.parser = parser;
			this.deunicoded = deunicoded;
		}

		@Override
		public String call() throws Exception
		{
			String stamp = UUID.randomUUID().toString();
			System.out.println(stamp);
			parser.saveContent(stamp);
			String result = deunicoded?parser.getContentWithoutUnicode():parser.getContent();
			System.out.println(stamp);
			return result;
		}
	}
}
