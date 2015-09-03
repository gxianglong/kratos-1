package com.gxl.kratos.utils.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "beans")
@XmlAccessorType(XmlAccessType.FIELD)
public class Beans {
	@XmlElement(name = "bean", nillable = true)
	private List<Bean> bean;

	public List<Bean> getBean() {
		return bean;
	}

	public void setBean(List<Bean> bean) {
		this.bean = bean;
	}
}