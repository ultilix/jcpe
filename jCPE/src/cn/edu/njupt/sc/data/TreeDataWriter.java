/**
 * 
 */
package cn.edu.njupt.sc.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author smartzxy
 * 
 */
public class TreeDataWriter implements DataWriter {

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
		Element root = dataSource.getRootElement();
		Element temp = root;
		for (String s : path) {
			temp = temp.element(s);
		}
		temp.setText(value);
		this.save();
		return 0;
	}

	/**
	 * Save addition into data file.
	 */
	public int save() {
		try {
			this.fos = new FileOutputStream("newData.xml");
			this.xmlWriter = new XMLWriter(fos);
			xmlWriter.write(this.dataSource);
			return 0;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return -1;
	}

	@Override
	public void close() {

		try {
			this.xmlWriter.close();
			this.fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Document dataSource;
	private FileOutputStream fos;
	private XMLWriter xmlWriter;

}
