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
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Immutable parser for file reading.
 *
 * Note: this class is thread safe.
 *
 * @author Fernando Farias (yiowns@gmail.com)
 * @version $Id$
 */
public class Parser {

    /**
     * Limit for the english ASCII chart.
     */
    public static final int ENG_CHARS_LIMIT = 0x80;
    /**
     * File this parser will use in its operations.
     */
    private final transient File file;

    /**
     * Parser constructor setting the associated file.
     *
     * @param file File for the parser.
     */
    Parser(
            final File file) {
        this.file = file;
    }

    /**
     * Get the file this parser is associated with.
     *
     * @return File for the parser.
     */
    public final File getFile() {
        return this.file;
    }

    /**
     * Get the complete content of the file.
     *
     * @return Content of the file.
     * @throws IOException In case the file cannot be opened or read.
     */
    public final String getContent() throws IOException {
        return new String(
                Files.readAllBytes(this.file.toPath()), StandardCharsets.UTF_8
        );
    }

    /**
     * Get the file contents filtered without unicode characters above 0x80.
     *
     * @return Filtered content.
     * @throws IOException Under error while opening or reading the file.
     */
    public final String getContentWithoutUnicode() throws IOException {
        final StringBuilder builder = new StringBuilder();
        try (FileInputStream in = new FileInputStream(this.file)) {
            int codePoint;
            do {
                codePoint = in.read();
                if (codePoint > 0 && codePoint < ENG_CHARS_LIMIT) {
                    builder.appendCodePoint(codePoint);
                }
            } while (codePoint != -1);
        }
        return builder.toString();
    }

    /**
     * Save content to file, overwriting existing content and creating the file
     * in case it does not already exists.
     *
     * @param content Content to write to file.
     * @throws IOException In case the file cannot be opened or written.
     */
    public final void saveContent(
            final String content) throws IOException {
        Files.write(
                this.file.toPath(), content.getBytes(StandardCharsets.UTF_8)
        );
    }
}
