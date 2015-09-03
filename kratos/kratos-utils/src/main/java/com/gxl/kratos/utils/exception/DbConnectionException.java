package com.gxl.kratos.utils.exception;

/**
 * 数据库链接异常
 * 
 * @author gaoxianglong
 */
public class DbConnectionException extends UtilsRuntimeException {
	private static final long serialVersionUID = 1305240660584213199L;

	public DbConnectionException(String str) {
		super(str);
	}
}