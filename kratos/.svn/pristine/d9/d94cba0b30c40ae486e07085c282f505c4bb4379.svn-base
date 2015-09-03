package com.gxl.kratos.utils.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bean")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bean {
	@XmlElement(name = "constructor-arg")
	private ConstructorArg constructor_arg;

	@XmlElement(name = "property")
	private List<Property> property;

	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "class")
	private String class_;

	public ConstructorArg getConstructor_arg() {
		return constructor_arg;
	}

	public void setConstructor_arg(ConstructorArg constructor_arg) {
		this.constructor_arg = constructor_arg;
	}

	public List<Property> getProperty() {
		return property;
	}

	public void setProperty(List<Property> property) {
		this.property = property;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClass_() {
		return class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}
}