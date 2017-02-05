import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

/**
 * This class is thread safe.
 */
public class Parser {
    private final File file;

    public Parser(File file) {
        if (file == null) {
            throw new IllegalArgumentException("Provided file is null");
        }
        this.file = new File(file.getPath());
    }

    public File getFile() {
        return new File(file.getPath());
    }

    public String getContent() throws IOException {
        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            output += (char) data;
        }
        return output;
    }

    public String getContentWithoutUnicode() throws IOException {
        FileInputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }

    public void saveContent(String content) throws IOException {
        FileOutputStream o = new FileOutputStream(file);
        FileLock lock = waitAndLock(o);
        try {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
        finally {
            lock.release();
        }
    }

    private FileLock waitAndLock(FileOutputStream fos) throws IOException {
        FileChannel channel = fos.getChannel();
        FileLock lock = null;
        while(lock == null) {
            lock = lock(channel);
        }
        return lock;
    }

    private FileLock lock(FileChannel fileChannel) throws IOException {
        try {
            return fileChannel.lock();
        }
        catch(OverlappingFileLockException e){
            return null;
        }
    }
}
