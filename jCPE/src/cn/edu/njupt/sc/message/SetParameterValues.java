package cn.edu.njupt.sc.message;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.edu.njupt.sc.GUI.CPEFrame;
import cn.edu.njupt.sc.data.DataReader;
import cn.edu.njupt.sc.data.DataWriter;
import cn.edu.njupt.sc.data.TreeDataReader;
import cn.edu.njupt.sc.data.TreeDataWriter;
import cn.edu.njupt.sc.data.TreePullWriter;

public class SetParameterValues extends Message {

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf)
			throws SOAPException {

	}	

	
	/**
	 * This method is just used for demonstrate in GUI.  
	 *//*
	@Override 
	  protected NameValue[] parseBody(SOAPBodyElement body, SOAPFactory f)throws SOAPException { 
	  Node parameterList=body.getFirstChild(); 
	  NodeList  nodeList = parameterList.getChildNodes(); 
	  int l=nodeList.getLength();
	  for (int i = 0; i < l; i++) { Node ParameterValueStruct =
	  nodeList.item(i); 
	  gui.insertDate("修改第" + (i + 1) + "个参数：" + "\n");
	  gui.insertDate("参数名：");
	  gui.insertDate(ParameterValueStruct.getChildNodes().item(0)
	  .getTextContent() + "\n"); gui.insertDate("参数新值：");
	  gui.insertDate(ParameterValueStruct.getChildNodes().item(1)
	  .getTextContent() + "\n"); }
	  gui.insertDate("**************************************************\n");
	  return null;
	  }
	 */
	// /*
	@Override
	protected NameValue[] parseBody(SOAPBodyElement body, SOAPFactory f)
			throws SOAPException {

		Node parameterList = body.getFirstChild();
		NodeList nodeList = parameterList.getChildNodes();
		int l = nodeList.getLength() ;
		DataWriter writer = new TreeDataWriter();
//		DataWriter writer= new TreePullWriter();
		
		for (int i = 0; i < l; i++) {
			Node ParameterValueStruct = nodeList.item(i);
			String parameter = ParameterValueStruct.getChildNodes().item(0)
					.getTextContent().trim();
			String value = ParameterValueStruct.getChildNodes().item(1)
					.getTextContent().trim();			
			writer.setSource("Data.xml");
			writer.update(parameter, value);
			
		}
		writer.save();
		writer.close();
		return null;
	}
	 //*/

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf,
			NameValue[] nameValue) throws SOAPException {
		// TODO Auto-generated method stub
		
	}
	
}
