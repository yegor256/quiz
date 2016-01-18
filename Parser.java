import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is thread safe.
 */
public class Parser {

    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private File file;

    public void setFile(File f) {
        lock.writeLock().lock();
        try {
            file = f;
        } finally {
            lock.writeLock().unlock();
        }
    }

    //removed due to getting file is not a Parser's responsibility
    //public synchronized File getFile() {
    //    return file;
    //}

    public String getContent() throws IOException {
        return readFile(new CommonReader());
    }

    public String getContentWithoutUnicode() throws IOException {
        return readFile(new AsciiReader());
    }

    private String readFile(Reader reader) throws IOException {
        lock.readLock().lock();
        try {
            try (FileInputStream i = new FileInputStream(file)) {
                return reader.read(i);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public void saveContent(String content) throws IOException {
        //1. here we can add some concurrent queue for submitting strings in multithreaded env
        //internal worker will take strings for writing

        //2. we can read/write strings with portions for big ones

        lock.writeLock().lock();
        try {
            try (FileOutputStream o = new FileOutputStream(file)) {
                try (FileChannel channel = o.getChannel()) {
                    channel.write(ByteBuffer.wrap(content.getBytes()));
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private interface Reader {
        String read(FileInputStream is) throws IOException;
    }

    private static class CommonReader implements Reader {
        @Override
        public String read(FileInputStream is) throws IOException {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = is.read()) > 0) {
                output.append((char) data);
            }
            return output.toString();
        }
    }

    private static class AsciiReader implements Reader {
        @Override
        public String read(FileInputStream is) throws IOException {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = is.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }
}