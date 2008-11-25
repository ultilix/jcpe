package cn.edu.njupt.sc.message;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.DOMException;

import cn.edu.njupt.sc.GUI.CPEFrame;

public abstract class Message {
	public Message() {

	}

	abstract protected void createBody(SOAPBodyElement body, SOAPFactory spf)
			throws SOAPException;
	
	abstract protected void createBody(SOAPBodyElement body, SOAPFactory spf,NameValue[] nameValue)
	throws SOAPException;

	abstract protected NameValue[] parseBody(SOAPBodyElement body, SOAPFactory f)
			throws SOAPException;
	
	public byte[] create() {
		
		try {
			MessageFactory mf = MessageFactory.newInstance();
			SOAPFactory spf = SOAPFactory.newInstance();

			SOAPMessage msg = mf.createMessage();
			SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
			env.addNamespaceDeclaration(CWMP, URN_CWMP);
			env.addNamespaceDeclaration("xsi",
					"http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd",
					"http://www.w3.org/2001/XMLSchema");
			msg.getSOAPHeader().addHeaderElement(
					spf.createName("ID", CWMP, URN_CWMP)).setValue(id);

			SOAPBodyElement bd = msg.getSOAPBody().addBodyElement(
					spf.createName(name, CWMP, URN_CWMP));
			createBody(bd, spf);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			msg.writeTo(out);
			out.flush();
			return out.toByteArray();

		} catch (SOAPException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public byte[] create(NameValue[] nameValue){
		
		try {
			MessageFactory mf = MessageFactory.newInstance();
			SOAPFactory spf = SOAPFactory.newInstance();

			SOAPMessage msg = mf.createMessage();
			SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
			env.addNamespaceDeclaration(CWMP, URN_CWMP);
			env.addNamespaceDeclaration("xsi",
					"http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd",
					"http://www.w3.org/2001/XMLSchema");
			msg.getSOAPHeader().addHeaderElement(
					spf.createName("ID", CWMP, URN_CWMP)).setValue(id);

			SOAPBodyElement bd = msg.getSOAPBody().addBodyElement(
					spf.createName(name, CWMP, URN_CWMP));
			createBody(bd, spf,nameValue);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			msg.writeTo(out);
			out.flush();
			return out.toByteArray();

		} catch (SOAPException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	static public String getRequestName(SOAPMessage msg) throws SOAPException {
		if (msg.getSOAPBody().hasFault())
			return "Fault";
		String name = getRequest(msg).getNodeName();
		if (name.startsWith(CWMP + ":"))
			name = name.substring(5);
		return name;
	}

	static public SOAPBodyElement getRequest(SOAPMessage msg)
			throws SOAPException {
		SOAPBodyElement request = null;
		Iterator i1 = msg.getSOAPBody().getChildElements();
		while (i1.hasNext()) {
			Node node = (Node) i1.next();
			if (node.getNodeType() == Node.ELEMENT_NODE)
				request = (SOAPBodyElement) node;
		}
		return request;
	}

	public NameValue[] parse(SOAPMessage msg) {
		try {
			SOAPFactory spf = SOAPFactory.newInstance();
			SOAPBodyElement soaprequest = getRequest(msg);

			SOAPHeader hdr = msg.getSOAPHeader();
			id = getHeaderElement(spf, hdr, "ID");
			name = getRequestName(msg);

			if (soaprequest != null) {

				return parseBody(soaprequest, spf);
			}

		} catch (NoSuchElementException e) {
			System.out.println("Message parse: No such element: "
					+ e.getMessage());
		} catch (SOAPException e) {
			System.out.println("Message parse: SOAPException: "
					+ e.getMessage());
		}
		return null;

	}

	public void parse(SOAPMessage msg, CPEFrame gui) {

		this.gui = gui;
		try {
			SOAPFactory spf = SOAPFactory.newInstance();
			SOAPBodyElement soaprequest = getRequest(msg);

			SOAPHeader hdr = msg.getSOAPHeader();
			id = getHeaderElement(spf, hdr, "ID");
			name = getRequestName(msg);

			if (soaprequest != null) {

				parseBody(soaprequest, spf);
			}

		} catch (NoSuchElementException e) {
			System.out.println("Message parse: No such element: "
					+ e.getMessage());
		} catch (SOAPException e) {
			System.out.println("Message parse: SOAPException: "
					+ e.getMessage());
		}
	}

	private String getHeaderElement(SOAPFactory spf, SOAPHeader hdr,
			String string) throws DOMException, SOAPException {

		return ((SOAPHeaderElement) hdr.getChildElements(
				spf.createName(string, CWMP, URN_CWMP)).next()).getValue();
	}

	protected CPEFrame gui;
	protected String name;
	protected String id = "123";
	public boolean noMore;
	protected static final String URN_CWMP = "urn:dslforum-org:cwmp-1-0";
	protected static final String CWMP = "cwmp";

}
