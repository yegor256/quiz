/*
 * Copyright (c) 2014, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Represents {@link Parser} returning ASCII-table characters only.
 *
 * @author Alexey Krylov (lexx)
 * @since 15.01.2016
 */
public class ASCIIParser extends Parser {
    /**
     * ASCII character set ending.
     */
    private static final int C_ASCII_END = 0x80;

    public ASCIIParser(File file) throws IOException {
        super(file);
    }

    @Override
    public String parse() throws IOException {
        try (FileInputStream i = new FileInputStream(file)) {
            StringBuilder sb = new StringBuilder();
            int data;
            while ((data = i.read()) > 0) {
                if (data < C_ASCII_END) {
                    sb.append((char) data);
                }
            }

            return sb.toString();
        }
    }
}
