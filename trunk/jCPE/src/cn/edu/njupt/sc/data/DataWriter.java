/**
 * 
 */
package cn.edu.njupt.sc.data;

import java.io.File;

/**
 * @author smartzxy@gmail.com
 * 
 */
public interface DataWriter {

	/**
	 * Update new parameter value in data file.
	 * 
	 * @param arg
	 *            the parameter needed to be updated.
	 * @param value
	 * @return
	 */
	public int update(String arg, String value);

	/**
	 * Insert new parameter value into data file.
	 * 
	 * @param arg
	 *            the parameter needed to insert.
	 * @param value
	 * @return
	 */
	public int insert(String arg, String value);

	/**
	 * Delete a specific parameter from data file.
	 * 
	 * @param arg
	 *            the parameter needed to be deleted.
	 * @return
	 */
	public int delet(String arg);

	/**
	 * Set data file source from String object.
	 * 
	 * @param file
	 */
	public void setSource(String file);

	/**
	 * Set data file source from File object.
	 * 
	 * @param file
	 */
	public void setSource(File file);
}
