/**
 * Copyright (c) 2015, New Company
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the New Company nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.newcompany.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * File read and write routines
 * <p/>
 * This class is thread safe.
 *
 * @author Dmitry Zaytsev (dmitry.zaytsev@gmail.com)
 * @version $Id$
 */
public class Parser {
    /**
     * Some constant.
     */
    public static final int MAGIC_NUMBER = 0x80;

    /**
     * The file description.
     */
    private File file;

    /**
     * Lock file modification.
     */
    private final transient Object mutex;

    /**
     * Default constructor.
     */
    public Parser() {
        this(null);
    }

    /**
     * Constructor with param.
     *
     * @param fle The file description
     */
    public Parser(final File fle) {
        this.mutex = new Object();
        this.file = fle;
    }

    /**
     * The file descriptor setter.
     *
     * @param fle New file descriptor
     */
    public final void setFile(final File fle) {
        synchronized (this.mutex) {
            this.file = fle;
        }
    }

    /**
     * The file descriptor getter.
     *
     * @return The file descriptor
     */
    public final File getFile() {
        return this.file;
    }

    /**
     * Read file content.
     *
     * @return File content
     * @throws IOException If IO error occurs
     */
    public final String getContent() throws IOException {
        try
                (
                        final FileInputStream inputStream =
                                new FileInputStream(this.file);
                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                inputStream,
                                                StandardCharsets.UTF_8
                                        )
                                )
                ) {
            final StringBuilder output = new StringBuilder();
            for (int data = reader.read(); data != -1; data = reader.read()) {
                output.append((char) data);
            }
            return output.toString();
        }
    }

    /**
     * Read file content without unicode symbols.
     *
     * @return Filtered file content
     * @throws IOException If IO error occurs
     */
    public final String getContentWithoutUnicode() throws IOException {
        try
                (
                        final FileInputStream inputStream =
                                new FileInputStream(this.file);
                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                inputStream,
                                                StandardCharsets.UTF_8
                                        )
                                );
                ) {
            final StringBuilder output = new StringBuilder();
            for (int data = reader.read(); data != -1; data = reader.read()) {
                if (data < MAGIC_NUMBER) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }

    /**
     * Write content to file.
     *
     * @param content Any string content
     * @throws IOException If IO error occurs
     */
    public final void saveContent(
            final String content) throws IOException {
        try (
                FileOutputStream outputStream = new FileOutputStream(this.file);
        ) {
            synchronized (this.mutex) {
                outputStream.write(content.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
