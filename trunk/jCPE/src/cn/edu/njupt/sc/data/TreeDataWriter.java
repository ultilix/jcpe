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
 * @author smartzxy
 * 
 */
public class TreeDataWriter implements DataWriter {

	private Document dataSource;

	public TreeDataWriter() {
	}

	public TreeDataWriter(File file) {
		SAXReader saxReader = new SAXReader();
		try {
			dataSource = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataWriter#delet(java.lang.String)
	 */
	@Override
	public int delet(String arg) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataWriter#insert(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int insert(String arg, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataWriter#setSource(java.lang.String)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataWriter#setSource(java.io.File)
	 */
	@Override
	public void setSource(File file) {

		SAXReader saxReader = new SAXReader();
		try {
			dataSource = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataWriter#update(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int update(String arg, String value) {

		String[] path = new Interpreter().Command2String(arg);
		// Get "InternetGatewayDevice" element.
		Element IGD = dataSource.getRootElement();
		Element temp = IGD;
		for (String s : path) {
			temp = temp.element(s);
		}
		temp.setText(value);
		return 0;
	}

}
