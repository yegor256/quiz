/**
 * Copyright (c) 2011-2014, Qulice.com
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package org.company.teamed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 * @author Francisco Javier (fjleontorrijo@gmail.com)
 * @version $Id$
 */
public class Parser {

    /**
     * Constant for Unicode characters.
     */
    private static final int UNICODE = 0x80;

    /**
     * File descriptor.
     */
    private final transient File file;

    /**
     * Constructor with file param.
     * @param parseable File to parse.
     */
    public Parser(final File parseable) {
        this.file = parseable;
    }

    /**
     * Read file content.
     * @param unicode True for read content with Unicode, false otherwise.
     * @return File content.
     * @throws IOException If I/O error occurs.
     */
    public final StringBuffer getContent(final boolean unicode)
            throws IOException {
        final StringBuffer output = new StringBuffer();
        synchronized (this.file) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(this.file);
                if (unicode) {
                    for (int data = fis.read(); data > 0; data = fis.read()) {
                        output.append((char) data);
                    }
                } else {
                    for (int data = fis.read(); data > 0; data = fis.read()) {
                        if (data < Parser.UNICODE) {
                            output.append((char) data);
                        }
                    }
                }
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }
        }
        return output;
    }

    /**
     * Save content to file.
     * @param content Content to save.
     * @throws IOException If I/O occurs.
     */
    public final void saveContent(final String content) throws IOException {
        synchronized (this.file) {
            if (content != null) {
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(this.file);
                    for (int pos = 0; pos < content.length(); pos += 1) {
                        fos.write(content.charAt(pos));
                    }
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
        }
    }

}
