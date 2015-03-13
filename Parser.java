package com.apts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {

	private File file;

	public synchronized void setFile(File f) {
		file = f;
	}
	public  File getFile() {
		return file;
	}
	public synchronized String getContent() throws IOException {

		FileInputStream i = null;
		StringBuilder output = new StringBuilder();
		try{

			i = new FileInputStream(file);
			int data;
			while ((data = i.read()) > 0) {
				output.append((char) data);
			}
		}finally{
			if(i!=null)
				i.close();
		}
		return output.toString();
	}
	public synchronized String getContentWithoutUnicode() throws IOException {
		FileInputStream i = null;
		StringBuilder output = new StringBuilder();
		try{
			i = new FileInputStream(file);
			int data;
			while ((data = i.read()) > 0) {
				if (data < 0x80) {
					output.append((char) data);
				}
			}
		}finally{
			if(i!=null)
				i.close();
		}
		return output.toString();
	}
	public synchronized void saveContent(String content) throws IOException {

		FileOutputStream o = null;
		try{
			o = new FileOutputStream(file);
			o.write(content.getBytes("utf8"));
		}finally{
			if(o!=null)
				o.close();
		}
	}
}

