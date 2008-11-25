package cn.edu.njupt.sc.message;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Download extends Message {

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf)
			throws SOAPException {

	}

	@Override
	protected NameValue[] parseBody(SOAPBodyElement body, SOAPFactory spf)
			throws SOAPException {

		gui.insertDate("=====获取到ACS端下载指令=====\n");
		NodeList nodeList = body.getChildNodes();
		int l = nodeList.getLength() - 1;
		for (int i = 0; i < l; i++) {
			Node node = nodeList.item(i);
			if (node.getNodeName().equalsIgnoreCase("FileType")) {
				gui.insertDate("文件类型： " + node.getTextContent() + "\n");
			} else if (node.getNodeName().equalsIgnoreCase("URL")) {
				gui.insertDate("URL地址： " + node.getTextContent() + "\n");
			} else if (node.getNodeName().equalsIgnoreCase("FileSize")) {
				gui.insertDate("文件尺寸： " + node.getTextContent() + "\n");
			} else if (node.getNodeName().equalsIgnoreCase("TargetFileName")) {
				gui.insertDate("文件重命名为： " + node.getTextContent() + "\n");
			} else {

			}
			
		}
		gui
		.insertDate("**************************************************\n");
		return null;

	}

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf,
			NameValue[] nameValue) throws SOAPException {
		// TODO Auto-generated method stub
		
	}

}
