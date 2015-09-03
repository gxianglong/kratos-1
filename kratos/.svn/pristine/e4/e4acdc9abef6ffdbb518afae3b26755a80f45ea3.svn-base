package com.gxl.kratos.utils.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "map")
@XmlAccessorType(XmlAccessType.FIELD)
public class Map {
	@XmlAttribute(name = "key-type")
	private String key_type;

	@XmlElement(name = "entry")
	private List<Entry> entry;

	public String getKey_type() {
		return key_type;
	}

	public void setKey_type(String key_type) {
		this.key_type = key_type;
	}

	public List<Entry> getEntry() {
		return entry;
	}

	public void setEntry(List<Entry> entry) {
		this.entry = entry;
	}
}