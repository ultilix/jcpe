package cn.edu.njupt.sc.message;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import cn.edu.njupt.sc.data.InformData;
import cn.edu.njupt.sc.data.TreeDataReader;

public class Inform extends Message {

	public Inform() {
		name = "Inform";
		count = 0;
		names = new ArrayList<String>();
		values = new ArrayList<String>();
		// initData();
	}

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf)
			throws SOAPException {
		SOAPElement deviceID = body.addChildElement("DeviceID", CWMP);
		deviceID.addAttribute(spf.createName("xsi:type"), CWMP
				+ ":DeviceIdStruct");
		SOAPElement dis = deviceID.addChildElement("DeviceIdStruct");
		SOAPElement mf = dis.addChildElement("Manufacturer");
		mf.setAttribute("xsi:type", "xsd:string(64)");
		mf.setValue("Manufacturer of the device");
		SOAPElement oui = dis.addChildElement("OUI");
		oui.setAttribute("xsi:type", "xsd:string(6)");
		oui.setValue("testReceive");
		SOAPElement pc = dis.addChildElement("ProductClass");
		pc.setAttribute("xsi:type", "xsd:string(64)");
		pc.setValue("mmm");
		SOAPElement sn = dis.addChildElement("SerialNumber");
		sn.setAttribute("xsi:type", "xsd:string(64)");
		sn.setValue("nnn");

		SOAPElement event = body.addChildElement("Event", CWMP);
		event.setAttribute("SOAP-ENV:arrayType", "xsd:EventStruct[64]");
		SOAPElement es = event.addChildElement("EventStruct");
		es.addChildElement("EventCode").setValue("0 BOOTSTRAP");
		es.addChildElement("CommandKey").setValue("");
		es = event.addChildElement("EventStruct");
		es.addChildElement("EventCode").setValue("1 BOOT");
		es.addChildElement("CommandKey").setValue("");

		SOAPElement maxEnvelopes = body.addChildElement("MaxEnvelopes", CWMP);
		maxEnvelopes.setAttribute("xsi:type", "xsd:int");
		maxEnvelopes.setValue("1");

		SOAPElement currentTime = body.addChildElement("CurrentTime", CWMP);
		currentTime.setAttribute("xsi:type", "xsd:dateTime");
		currentTime.setValue(new Date().toString());

		SOAPElement retryCount = body.addChildElement("RetryCount", CWMP);
		retryCount.setAttribute("xsi:type", "xsd:int");
		retryCount.setValue(String.valueOf(count));

		SOAPElement parameterList = body.addChildElement("ParameterList", CWMP);
		parameterList.addAttribute(spf.createName("SOAP-ENV:arrayType"),
				"xsd:ParameterValueStruct[" + String.valueOf(names.size())
						+ "]");
		for (int i = 0; i < names.size(); i++) {
			SOAPElement param = parameterList
					.addChildElement("ParameterValueStruct");
			param.addChildElement("Name").setValue((String) names.get(i));
			SOAPElement v = param.addChildElement("Value");
			v.setValue((String) values.get(i));
			v.setAttribute("xsi:type", "xsd:string");
		}
	}

	@Override
	protected NameValue[] parseBody(SOAPBodyElement body, SOAPFactory f)
			throws SOAPException {
		// TODO Auto-generated method stub
		return null;
	}

	private void initData() {

		File file = new File("Data.xml");
		TreeDataReader reader = new TreeDataReader(file);
		InformData informData = new InformData("InformData.xml");
		for (String s : informData.getParameterArray()) {
			names.add(s);
			values.add(reader.read(s));
		}
	}

	/**
	 * @return the names
	 */
	public List<String> getNames() {
		return names;
	}

	/**
	 * @param names
	 *            the names to set
	 */
	public void setNames(List<String> names) {
		this.names = names;
	}

	/**
	 * @return the values
	 */
	public List<String> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf,
			NameValue[] nameValue) throws SOAPException {
		// TODO Auto-generated method stub
		
	}
	private int count;
	private List<String> names;
	private List<String> values;

}
