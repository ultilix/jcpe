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

	/**
	 * Set the data file from the given file name such as "data.xml".
	 * 
	 * @param file
	 */
	public void setSource(String file);

	/**
	 * Set the data file from the given File object.
	 * 
	 * @param file	 *
	 */
	public void setSource(File file);

	/**
	 * Query specific parameter's value from given path.
	 * 
	 * @param arg
	 *            parameter path.
	 * @return parameter's value;This method does not return null.
	 */
	public String read(String arg);

	/**
	 * Query specific parameter's attribute from given path.
	 * 
	 * @param arg
	 *            parameter path
	 * @return attribute value
	 */
	public String readAttribute(String arg, String attribute);
}
