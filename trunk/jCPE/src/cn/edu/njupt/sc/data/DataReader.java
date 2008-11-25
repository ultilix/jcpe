/**
 * 
 */
package cn.edu.njupt.sc.data;

import java.io.File;

/**
 * @author smartzxy@gmail.com
 * 
 */
public interface DataReader {

	public void setSource(String file);

	public void setSource(File file);

	public String read(String arg);
	
	public String readAttribute(String arg, String attribute);
}
