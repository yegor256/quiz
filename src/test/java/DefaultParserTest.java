import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public class DefaultParserTest {
	private File f;
	private DefaultParser p;

	@Before
	public void setUp() {
		this.f = Mockito.mock(File.class);
		this.p = Mockito.spy(new DefaultParser(this.f));
	}

	@Test
	public void testParse() {
		Assert.assertEquals(Optional.of('\u0400'), this.p.parse(1024));
	}

	@Test
	public void testGetContent() throws Exception {
		InputStream in = Mockito.mock(InputStream.class);

		Mockito.doReturn(in).when(this.p).getInputStream(this.f);
		Mockito.when(in.read()).thenReturn(1, 1024, 0, -1);
		Mockito.doReturn(Optional.of('\u0002')).when(this.p).parse('\u0001');
		Mockito.doReturn(Optional.empty()).when(this.p).parse('\u0400');
		Mockito.doReturn(Optional.of('\u0001')).when(this.p).parse('\u0000');

		String c = this.p.getContent();
		Assert.assertEquals("\u0002\u0001", c);

		Mockito.verify(in).close();
	}

	@Test
	public void testGetContentFailure() throws Exception {
		InputStream in = Mockito.mock(InputStream.class);

		IOException ex = new IOException();
		Mockito.doReturn(in).when(this.p).getInputStream(this.f);
		Mockito.when(in.read()).thenThrow(ex);

		try {
			this.p.getContent();
			Assert.fail();
		} catch (final Exception e){
			Assert.assertSame(e, ex);
		}

		Mockito.verify(in).close();
	}

	@Test
	public void testSaveContent() throws Exception {
		OutputStream out = Mockito.mock(OutputStream.class);

		Mockito.doReturn(out).when(this.p).getOutputStream(this.f);

		this.p.saveContent("\u0001\u0400\u0000");

		Mockito.verify(out, Mockito.times(3)).write(Mockito.anyByte());
		InOrder inOrder = Mockito.inOrder(out, out, out);
		inOrder.verify(out).write(1);
		inOrder.verify(out).write(1024);
		inOrder.verify(out).write(0);

		Mockito.verify(out).close();
	}

	@Test
	public void testSaveContentFailure() throws Exception {
		OutputStream out = Mockito.mock(OutputStream.class);

		IOException ex = new IOException();
		Mockito.doReturn(out).when(this.p).getOutputStream(this.f);
		Mockito.doThrow(ex).when(out).write('\u0400');

		try {
			this.p.saveContent("\u0001\u0400\u0000");

			Assert.fail();
		} catch (final Exception e){
			Assert.assertSame(e, ex);
		}

		Mockito.verify(out, Mockito.times(2)).write(Mockito.anyByte());
		InOrder inOrder = Mockito.inOrder(out, out, out);
		inOrder.verify(out).write(1);
		inOrder.verify(out).write(1024);

		Mockito.verify(out).close();
	}
}
