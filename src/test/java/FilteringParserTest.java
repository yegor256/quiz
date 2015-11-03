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

public class FilteringParserTest {
	@Test
	public void testParse() {
		FilteringParser p = new FilteringParser(null);
		Assert.assertEquals(Optional.of('\u007f'), p.parse(127));
		Assert.assertEquals(Optional.empty(), p.parse(128));
	}
}
