package cn.edu.njupt.sc.message;

import java.util.List;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import cn.edu.njupt.sc.data.TreeDataReader;

public class GetParameterNamesResponse extends Message {

	public GetParameterNamesResponse() {
		this.name = "GetParameterNamesResponse";
	}

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf)
			throws SOAPException {
		// TODO Auto-generated method stub

	}

	@Override
	protected NameValue[] parseBody(SOAPBodyElement body, SOAPFactory spf)
			throws SOAPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf,
			NameValue[] nameValue) throws SOAPException {

		this.setStruct(nameValue);
		SOAPElement parameterList = body.addChildElement("ParameterList", CWMP);
		parameterList
				.addAttribute(spf.createName("SOAP-ENV:arrayType"),
						"xsd:ParameterInfoStruct["
								+ String.valueOf(names.size()) + "]");
		for (int i = 0; i < names.size(); i++) {
			SOAPElement param = parameterList
					.addChildElement("ParameterInfoStruct");
			param.addChildElement("Name").setValue((String) names.get(i));
			SOAPElement v = param.addChildElement("Writable");
			v.setValue((String) states.get(i));
			v.setAttribute("xsi:type", "xsd:boolean");
		}
	}

	private void setStruct(NameValue[] nameValue) {

		TreeDataReader reader = new TreeDataReader();
		reader.setSource("Data.xml");
		for (NameValue nv : nameValue) {

			this.names.add(nv.getName());
			this.states.add(reader.readAttribute(nv.getName(),"Write"));
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
		return states;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(List<String> values) {
		this.states = values;
	}

	private List<String> names;
	private List<String> states;
}
