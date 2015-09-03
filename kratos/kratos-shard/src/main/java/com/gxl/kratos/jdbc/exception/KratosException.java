package com.gxl.kratos.jdbc.exception;

/**
 * kratos非运行时异常超类
 * 
 * @author gaoxianglong
 */
public class KratosException extends Exception {
	private static final long serialVersionUID = -6153893412860260199L;

	public KratosException(String str) {
		super(str);
	}
}