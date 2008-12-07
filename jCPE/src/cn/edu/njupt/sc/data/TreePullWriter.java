/**
 * 
 */
package cn.edu.njupt.sc.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;

/**
 * @author smartzxy@gmail.com
 * 
 */
public class TreePullWriter implements DataWriter {

	public TreePullWriter() {

	}

	public TreePullWriter(String file) {
		this.setSource(file);
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
		this.setSource(f);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataWriter#setSource(java.io.File)
	 */
	@Override
	public void setSource(File file) {

		try {
			this.fos = new FileOutputStream("newXML.xml");
			this.writer = XMLOutputFactory.newInstance().createXMLStreamWriter(
					fos);

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataWriter#update(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int update(String arg, String value) {

		String[] path = new Interpreter().Command2String(arg);
		OMElement root = this.builder.getDocumentElement();
		OMElement temp = root;
		for (String s : path) {
			temp = temp.getFirstChildWithName(new QName(s));
		}
		try {
			temp.setText(value);
			root.serialize(this.fos);
			// this.writer.flush();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataWriter#save()
	 */
	@Override
	public int save() {

		try {
			this.writer.flush();
			return 0;
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			this.close();

		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.njupt.sc.data.DataWriter#close()
	 */
	@Override
	public void close() {

		try {
			this.reader.close();
			this.fis.close();
			this.writer.close();
			this.fos.close();

		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private FileOutputStream fos;
	private XMLStreamWriter writer;
	private StAXOMBuilder builder;
	private FileInputStream fis;
	private XMLStreamReader reader;
}
