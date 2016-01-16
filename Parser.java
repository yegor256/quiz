package parse.com;

import java.io.*;
import java.nio.charset.Charset;

/**
 * This class is thread safe.
 */
public class Parser {
    private File file;
    private Charset charset;
    Parser(File file, CharSet charset) {
        this.file = f;
        this.charset = charset;
    }
    public File getFile() {
        return file;
    }
    public CharSet getCharSet() {
        return charset;
    }
    public synchronized String getContent(boolean withUnicode) {
        try (BufferedReader i = new BufferedReader(charset == null ? new InputStreamReader(new FileInputStream(file))
                                                                     : new InputStreamReader(new FileInputStream(file), charset))) {
            StringBuilder output = "";
            while (( int data = i.read())>0) {
                if (withUnicode) {
                    output += (char) data;
                } else {
                    if (data < 0x80) {
                        output += (char) data;
                    }
                }
            }
            return output;
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    public synchronized void saveContent(String content) {
        try(BufferedWriter o = new BufferedWriter(charset == null ? new OutputStreamWriter(new FileOutputStream(file))
                                                                    : new OutputStreamWriter(new FileOutputStream(file), charset)) {
            for(int i = 0;i<content.length();i+=1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
