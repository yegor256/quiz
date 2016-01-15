/*
 * Copyright (c) 2014, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents simple thread-safe content saver.
 *
 * @author Alexey Krylov (lexx)
 * @since 15.01.2016
 */
public class ContentSaver {
    protected File file;
    protected Lock lock;

    public File getFile() {
        return file;
    }

    public ContentSaver(File file) {
        if (file == null) {
            throw new NullPointerException("Specified file is null");
        }
        // Making sure that path will be writable;
        file.mkdirs();
        lock = new ReentrantLock();
        this.file = file;
    }

    public void save(String content) throws IOException {
        lock.lock();
        try {
            try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
                out.print(content);
            }
        } finally {
            lock.unlock();
        }
    }
}
