package quiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is thread safe.
 */
public class Parser {

    private static final int FINAL_UNICODE_VALUE = 0x80;

    /*
    If passed in getContent() method, contents below
    @see #FINAL_UNICODE_VALUE
    will be returned.
    Pass this as parameter to get content without encoding
     */
    public static final int WITHOUT_ENCODING = 1;
     /*
    If passed in getContent() method, contents below
    @see #FINAL_UNICODE_VALUE
    will be returned.
    Pass this as parameter to get content, that is, even the encoding
     */
    public static final int WITH_ENCODING = 0;

    /*According to Java convention, a field name should start with m*/
    private final File mFile;

    //Using constructor to initialize file to avoid changing file
    public Parser(File f) {
        mFile = f;
    }

    public synchronized File getFile() {
        return mFile;
    }

    /*
     read() function returns -1 if there is no more data because the end of
     the file has been reached.
     This method has been deprecated by getContent(int) method
     */
    @Deprecated
    public synchronized String getContent() throws IOException {
        StringBuilder stringBuilder;
        try (FileInputStream fileInputStream = new FileInputStream(mFile)) {
            stringBuilder = new StringBuilder();
            int data;
            while ((data = fileInputStream.read()) != -1) {
                stringBuilder.append(String.valueOf(data));
            }
        }
        return stringBuilder.toString();
    }

    /*
     used a static field for filtering out unicode
    This method has been deprecated by getContent(int) method
     */
    @Deprecated
    public synchronized String getContentWithoutUnicode() throws IOException {
        StringBuilder stringBuilder;
        try (FileInputStream fileInputStream = new FileInputStream(mFile)) {
            stringBuilder = new StringBuilder();
            int data;
            while ((data = fileInputStream.read()) != -1) {
                if (data < FINAL_UNICODE_VALUE) {
                    stringBuilder.append(String.valueOf(data));
                }
            }
        }
        return stringBuilder.toString();
    }

    /*
     Returns the content of file as string type.
    
     @param type The desired type.
     @see #WITHOUT_ENCODING
     @see #WITH_ENCODING
     */
    public synchronized String getContent(int type) throws IOException {
        StringBuilder stringBuilder;
        try (FileInputStream fileInputStream = new FileInputStream(mFile)) {
            stringBuilder = new StringBuilder();
            int data;
            while ((data = fileInputStream.read()) != -1) {
                if (type == WITHOUT_ENCODING) {
                    if (data < FINAL_UNICODE_VALUE) {
                        stringBuilder.append(String.valueOf(data));
                    }
                } else {
                    stringBuilder.append(String.valueOf(data));
                }
            }
        }
        return stringBuilder.toString();
    }

    /* Using try-with-resources statement closes any resources(file in this
     case) itself
     Use synchronized keyword to access the file in atomic way.
     */
    public synchronized void saveContent(String content)
            throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(mFile)) {
            printWriter.println(content);
        }
    }
}

