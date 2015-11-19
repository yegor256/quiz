/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.teamed.quiz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Parser class unit test.
 *
 * @author Fernando Farias (yiowns@gmail.com)
 * @version $Id$
 */
public class ParserTest {

    /**
     * Temporary file reference.
     */
    private transient File file;
    /**
     * Parser object to test.
     */
    private transient Parser parser;

    /**
     * Setup temporary file for the tests.
     *
     * @throws IOException Under file error.
     */
    @Before
    public final void setUp() throws IOException {
        this.file = File.createTempFile("parser_test", ".txt");
        Assert.assertNotNull("Error creating temporary file.", this.file);
        this.parser = new Parser(this.file);
        Assert.assertNotNull("Error creating parser.", this.parser);
    }

    /**
     * Test the get method works.
     */
    @Test
    public final void testGetFile() {
        Assert.assertEquals(
                "File is not the same.",
                this.file, this.parser.getFile()
        );
    }

    /**
     * Test content retrieval.
     *
     * @throws IOException Under file error.
     */
    @Test
    public final void testGetContent() throws IOException {
        final String content = "Lorem Ipsum";
        this.parser.saveContent(content);
        Assert.assertEquals(
                "Content is not the same.",
                content, this.parser.getContent()
        );
    }

    /**
     * Test filtered content retrieval.
     *
     * @throws IOException Under file error.
     */
    @Test
    public final void testGetContentWithoutUnicode() throws IOException {
        this.parser.saveContent("Lórem Ipsúm");
        Assert.assertEquals(
                "Content without unicode is invalid.",
                "Lrem Ipsm", this.parser.getContentWithoutUnicode()
        );
    }

    /**
     * Test content saving against the actual file contents.
     *
     * @throws IOException Under file error.
     */
    @Test
    public final void testSaveContent() throws IOException {
        final String content = "Lorem ipsum dolor sit.";
        this.parser.saveContent(content);
        Assert.assertEquals(
                "Saved content is not valid.",
                content, new String(Files.readAllBytes(this.file.toPath()))
        );
    }
}
