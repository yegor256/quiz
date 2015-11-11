import java.io.*;

public class Parser {

    public static final int BUFFER_SIZE = 1024;

    // TODO: actually its better to pass a stream instead of file because its more universal
    public String getContent(File file) throws IOException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toString("UTF-8");
        }
    }

    // TODO: actually its better to pass a stream instead of file because its more universal
    public String getContentWithoutUnicode(File file) throws IOException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            int data;
            while ((data = inputStream.read()) > 0) {
                if (isAnAsciiSymbol(data)) {
                    byteArrayOutputStream.write(data);
                }
            }
            return byteArrayOutputStream.toString("UTF-8");
        }
    }

    // TODO: actually its better to pass a stream instead of file because its more universal
    public void saveContent(String content, File file) throws IOException {
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
             ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes())) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    boolean isAnAsciiSymbol(int character) {
        return character < 0x80;        // ascii symbols start from 0x00 to 0x7F inclusively
    }

}
