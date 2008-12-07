/**
 * This reader uses Axiom parser.
 */
package cn.edu.njupt.sc.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;

/**
 * @author smartzxy@gmail.com
 * 
 */
public class TreePullReader implements DataReader {

	public TreePullReader() {
	}

	public TreePullReader(String file) {

		this.setSource(file);
	}

	public TreePullReader(File file) {

		this.setSource(file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataReader#read(java.lang.String)
	 */
	@Override
	public String read(String arg) {

		OMElement root = this.builder.getDocumentElement();
		OMElement temp = root;
		String[] args = new Interpreter().Command2String(arg);
		for (String s : args) {
			temp = temp.getFirstChildWithName(new QName(s));
		}
		return temp.getText().trim();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataReader#readAttribute(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String readAttribute(String arg, String attribute) {

		OMElement root = this.builder.getDocumentElement();
		OMElement temp = root;
		String[] args = new Interpreter().Command2String(arg);
		for (String s : args) {
			temp = temp.getFirstChildWithName(new QName(s));
		}
		String value = temp.getAttributeValue(new QName(attribute));
		if (value != null)
			return value;
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataReader#setSource(java.lang.String)
	 */
	@Override
	public void setSource(String file) {

		File f = new File(file);
		this.setSource(f);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataReader#setSource(java.io.File)
	 */
	@Override
	public void setSource(File file) {

		try {
			this.fis = new FileInputStream(file);
			this.reader = XMLInputFactory.newInstance().createXMLStreamReader(
					fis);
			this.builder = new StAXOMBuilder(reader);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}

	}

	/**
	 * Close XMLStreamReader and FileInputStream.
	 */
	public void close() {
		try {
			this.reader.close();
			this.fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	private StAXOMBuilder builder;
	private FileInputStream fis;
	private XMLStreamReader reader;

}
