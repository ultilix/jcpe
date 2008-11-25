/**
 * 
 */
package cn.edu.njupt.sc.data;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author smartzxy@gmail.com
 * 
 */
public class InformData implements Data {

	private File f;
	private List<String> taskList;
	private String[] taskArray;

	public InformData() {

	}

	public InformData(String file) {
		f = new File(file);
	}

	/**
	 * @return the f
	 */
	public File getFile() {
		return f;
	}

	/**
	 * @param f
	 *            the f to set
	 */
	public void setFile(File f) {
		this.f = f;
	}

	/**
	 * @return the taskList
	 */
	public List<String> getParameterList() {
		SAXReader saxReader = new SAXReader();
		try {
			Document pSource = saxReader.read(f);
			Element root = pSource.getRootElement();
			List<Element> list = root.elements("Parameter");
			for (Iterator<Element> i = list.iterator(); i.hasNext();) {
				Element parameter = i.next();
				this.taskList.add(parameter.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return taskList;
	}

	/**
	 * @param taskList
	 *            the taskList to set
	 */
	public void setParameterList(List<String> taskList) {
		this.taskList = taskList;
	}

	/**
	 * @return the taskArray
	 */
	public String[] getParameterArray() {
		SAXReader saxReader = new SAXReader();
		try {
			Document pSource = saxReader.read(f);
			Element root = pSource.getRootElement();
			List<Element> list = root.elements("Parameter");
			this.taskArray = new String[list.size()];
			int index = 0;
			for (Iterator<Element> i = list.iterator(); i.hasNext(); index++) {
				Element parameter = i.next();
				this.taskArray[index] = parameter.getText();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return taskArray;
	}

	/**
	 * @param taskArray
	 *            the taskArray to set
	 */
	public void setParameterArray(String[] taskArray) {
		this.taskArray = taskArray;
	}

}
