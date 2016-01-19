import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileSaver
 *
 * @author Alexander Kontsur (a.kontsur)
 * @since 19.01.2016
 */
public class FileSaver {

    public void saveContent(String content, FileOutputStream os) throws IOException {
        if (content == null || os == null) {
            return;
        }
        try (FileChannel channel = os.getChannel()) {
            channel.write(ByteBuffer.wrap(content.getBytes()));
        }
    }
}
