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
	 * @return the value of parameter;This method does not return null.
	 */
	public String read(String arg);

	/**
	 * Query specific parameter's attribute from given path.
	 * 
	 * @param arg
	 *            parameter path
	 * @return the attribute value for the attribute with the given name, or
	 *         null if there is no such attribute, or the empty string if the
	 *         attribute value is empty.
	 */
	public String readAttribute(String arg, String attribute);
}
