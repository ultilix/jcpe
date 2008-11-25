package cn.edu.njupt.sc.message;

import java.util.List;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import cn.edu.njupt.sc.data.TreeDataReader;

public class GetParameterValuesResponse extends Message {

	public GetParameterValuesResponse() {
		name = "GetParameterValuesResponse";
	}

	@Override
	protected void createBody(SOAPBodyElement body, SOAPFactory spf)
			throws SOAPException {

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

		this.setStruct(nameValue);
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

	private void setStruct(NameValue[] nameValue) {

		TreeDataReader reader = new TreeDataReader();
		reader.setSource("Data.xml");
		for (NameValue nv : nameValue) {

			this.names.add(nv.getName().trim());
			this.values.add(reader.read(nv.getValue().trim()));
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

	private List<String> names;
	private List<String> values;
}
