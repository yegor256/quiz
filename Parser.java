

import java.io.*;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;


    private boolean contentRead = false;
    private String content;

    private boolean contentWithoutUnicodeRead = false;
    private String contentWithoutUnicode;


    public synchronized void setFile(File f) {
        file = f;
        contentRead = false;
        content = null;

        contentWithoutUnicodeRead = false;
        contentWithoutUnicode = null;
    }

    public File getFile() {
        return file;
    }

    /**
     * Performs necessary file checks and return FileInputStream
     *
     * @return input stream of specified file;
     * @throws IOException
     */
    private FileInputStream getFileInputStream() throws IOException {
        //first we need to verify that file exists and is not a directory
        if (file == null) {
            throw new IOException("OOPS File is not specified");
        } else if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }

        return new FileInputStream(this.file);
    }



    /**
     * Reads the content of the file  and stores it in String property;
     *
     * StringBuffer is used to prevent creation of new String during each read operations
     *
     * this method modifies the content of the class, thus it should be synchronized
     */
    private synchronized void readContent() throws IOException {
        FileInputStream i = this.getFileInputStream();;
        // we will initialize an initial capacity of String buffer
        // initial capacity can be measured better, when it is known average file size to be processed. Otherwise
        // it is possible to
        StringBuffer contentBuffer = new StringBuffer(1024 * 4);
        int n = 0;
        while (-1 != (n = i.read())) {
            contentBuffer.append(n);
        }
        i.close();

        this.content = contentBuffer.toString();
        this.contentRead = true;
    }

    /**
     * See comments for readContent();
     *
     * @throws IOException
     */
    private synchronized void readContentWithoutUnicode() throws IOException {
        FileInputStream i = this.getFileInputStream();;
        // we will initialize an initial capacity of String buffer
        // initial capacity can be measured better, when it is known average file size to be processed. Otherwise
        // it is possible to
        StringBuffer contentBuffer = new StringBuffer(1024 * 4);
        int n = 0;
        while (-1 != (n = i.read())) {
            if (n < 0x80) {
                contentBuffer.append(n);
            }
        }
        i.close();

        this.contentWithoutUnicode = contentBuffer.toString();
        this.contentWithoutUnicodeRead = true;
    }


    /**
     * It is expected that content of the file is read several times, thus it is better to make its content as a
     * property. and initialize during first access
     *
     * @return
     * @throws IOException
     */
    public String getContent() throws IOException {
        if (!contentRead) {
            this.readContent();
        }

        return content;
    }

    /**
     * Same approach as for getContent()
     *
     * @return
     * @throws IOException
     */
    public String getContentWithoutUnicode() throws IOException {
        if (!contentWithoutUnicodeRead) {
            this.readContentWithoutUnicode();
        }

        return contentWithoutUnicode;
    }


    /**
     * Performs necessary file checks and return FileOutputStream
     *
     * @return output stream of specified file;
     * @throws IOException
     */
    private synchronized FileOutputStream getFileOutputStream() throws IOException {
        //first we need to verify that file exists and is not a directory
        if (file == null) {
            throw new IOException("OOPS File is not specified");
        } else if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written");
            }
        } else {
            if (!file.createNewFile()) {
                throw new IOException("File '" + file + "' cannot be created");
            }
        }

        return new FileOutputStream(this.file);
    }


    /**
     * during write operation we verify that we can actually write to file. and rely on write byte buffers;
     *
     * @param content
     * @throws IOException
     */
    public void saveContent(String content) throws IOException {
        if (content != null) {
            FileOutputStream o = this.getFileOutputStream();
            o.write(content.getBytes());
            o.close();
        } else {
            // have to do or throw something = we cannot write null strings
        }
    }
}
