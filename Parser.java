import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Parser {
  
  // parser class can be a simple facade pattern with APIs for parsing only
  public static String getContent(File file) throws IOException {
    if(file == null) return null;
    String output = null;
    // synchronized on the file object only; no need to synchronized on parser
    synchronized(file){
      FileInputStream i = null;
	  try{
        i = new FileInputStream(file);
        int data;
        while ((data = i.read()) > 0) {
          output += (char) data;
        }
	  }catch(Exception e){
		  throw e;
	  }finally{
		  if(i!=null)
			  i.close();
	  }
    }
    return output;
  }
  
  // further to do
  // can change the calls to something like getContent(File file, int filter)
  // but this is good for 15 minutes exercise
  public static String getContentWithoutUnicode(File file) throws IOException {
	    if(file == null) return null;
	    String output = null;
	    
	    synchronized(file){
	      FileInputStream i = null;
		  try{
	        i = new FileInputStream(file);
	        int data;
	        while ((data = i.read()) > 0) {
	        	if (data < 0x80) {
	                output += (char) data;
	            }
	        }
		  }catch(Exception e){
			  throw e;
		  }finally{
			  if(i!=null)
				  i.close();
		  }
	   }
	    return output;
   }
 }

  //parser should not modify file; so move the savecontent out of parser

