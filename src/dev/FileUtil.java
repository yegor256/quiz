package dev;

import java.io.File;
import java.io.IOException;

public interface FileUtil {

	public abstract void setFile(File f);

	public abstract File getFile();

	public abstract String getContent() throws IOException;

	public abstract String getContentWithoutUnicode() throws IOException;

	public abstract void saveContent(String content) throws IOException;

}