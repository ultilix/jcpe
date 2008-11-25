package cn.edu.njupt.sc.message;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GetParameterValues extends Message {

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf)
			throws SOAPException {

	}

	@Override
	protected NameValue[] parseBody(SOAPBodyElement body, SOAPFactory f)
			throws SOAPException {

		Node parameterNames = body.getFirstChild();
		NodeList strings = parameterNames.getChildNodes();
		int length = strings.getLength();
		NameValue[] nameValue = new NameValue[length];
		for (int i = 0; i < length; i++) {
			Node string = strings.item(i);
			nameValue[i].setName(string.getTextContent());
		}
		return nameValue;
	}

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf,
			NameValue[] nameValue) throws SOAPException {
		// TODO Auto-generated method stub

	}

}
