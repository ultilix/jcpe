package cn.edu.njupt.sc.message;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;


public class GetParameterNames extends Message {

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf)
			throws SOAPException {
		// TODO Auto-generated method stub

	}

	@Override
	protected NameValue[] parseBody(SOAPBodyElement body, SOAPFactory f)
			throws SOAPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf,
			NameValue[] nameValue) throws SOAPException {
		// TODO Auto-generated method stub
		
	}

}
