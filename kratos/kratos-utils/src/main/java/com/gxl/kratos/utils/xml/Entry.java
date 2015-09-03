package com.gxl.kratos.utils.xml;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class Entry {
	@XmlAttribute(name = "key")
	private String key;

	@XmlAttribute(name = "value-ref")
	private String value_ref;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue_ref() {
		return value_ref;
	}

	public void setValue_ref(String value_ref) {
		this.value_ref = value_ref;
	}
}