/**
 * 
 */
package cn.edu.njupt.sc.data;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author smartzxy@gmail.com
 * 
 */
public class TreeDataReader implements DataReader {

	private Document dataSource;

	public TreeDataReader() {
	}

	/**
	 * Construct a TreeDataReader with specific data file.
	 * 
	 * @param file
	 */
	public TreeDataReader(File file) {
		SAXReader saxReader = new SAXReader();
		try {
			dataSource = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setSource(String file) {
		File f = new File(file);
		SAXReader saxReader = new SAXReader();
		try {
			dataSource = saxReader.read(f);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setSource(File file) {
		SAXReader saxReader = new SAXReader();
		try {
			dataSource = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Query specific parameter's value from given path.
	 * 
	 * @param arg
	 *            parameter path.
	 * @return parameter's value;This method does not return null.
	 */
	@Override
	public String read(String arg) {
		// Get "InternetGatewayDevice" element.
		Element IGD = dataSource.getRootElement();

		Element temp = IGD;
		String[] args = new Interpreter().Command2String(arg);
		for (String s : args) {
			temp = temp.element(s);
		}
		return temp.getTextTrim();
	}

	/**
	 * Query specific parameter's "Write" attribute from given path.
	 * 
	 * @param arg
	 *            parameter path
	 * @return attribute value of "Write"
	 */
	@Override
	public String readAttribute(String arg, String attribute) {

		// Get "InternetGatewayDevice" element.
		Element IGD = dataSource.getRootElement();

		Element temp = IGD;
		String[] args = new Interpreter().Command2String(arg);
		for (String s : args) {
			temp = temp.element(s);
		}
		String value = temp.attributeValue(attribute);
		if (value != null)
			return value;
		else
			return "false";
	}

}
